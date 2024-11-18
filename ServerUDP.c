//Compile - gcc ServerUDP.c -o ServerUDP.exe -lws2_32
// Run - ./ServerUDP.exe
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

#define SERVER_PORT 2000
#define BUFFER_SIZE 1024

int main() {
    WSADATA wsaData;
    SOCKET serverSocket;
    struct sockaddr_in serverAddr, clientAddr;
    char receiveBuffer[BUFFER_SIZE], sendBuffer[BUFFER_SIZE];
    int result, clientAddrLen = sizeof(clientAddr);

    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        printf("WSAStartup failed\n");
        return 1;
    }

    // Create UDP socket
    serverSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (serverSocket == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return 1;
    }

    // Set up the server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(SERVER_PORT);
    serverAddr.sin_addr.s_addr = INADDR_ANY;

    // Bind the socket to the address and port
    result = bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr));
    if (result == SOCKET_ERROR) {
        printf("Bind failed\n");
        closesocket(serverSocket);
        WSACleanup();
        return 1;
    }

    printf("Server is listening on port %d...\n", SERVER_PORT);

    // Communication loop
    while (1) {
        // Receive message from client
        result = recvfrom(serverSocket, receiveBuffer, sizeof(receiveBuffer), 0, 
                          (struct sockaddr *)&clientAddr, &clientAddrLen);
        if (result == SOCKET_ERROR) {
            printf("Receive failed\n");
            break;
        }
        receiveBuffer[result] = '\0';  // Null-terminate the received message
        printf("[Client]: %s\n", receiveBuffer);

        // Exit condition
        if (strcmp(receiveBuffer, "exit") == 0)
            break;

        // Get server's reply
        printf("Server: ");
        fgets(sendBuffer, sizeof(sendBuffer), stdin);
        sendBuffer[strcspn(sendBuffer, "\n")] = '\0';  // Remove trailing newline

        // Send server's reply back to the client
        result = sendto(serverSocket, sendBuffer, strlen(sendBuffer), 0,
                        (struct sockaddr *)&clientAddr, clientAddrLen);
        if (result == SOCKET_ERROR) {
            printf("Send failed\n");
            break;
        }

        // Exit condition
        if (strcmp(sendBuffer, "exit") == 0)
            break;
    }

    // Cleanup
    closesocket(serverSocket);
    WSACleanup();

    return 0;
}
