//Compile - gcc ClientUDP.c -o ClientUDP.exe -lws2_32
// Run - ./ClientUDP.exe
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

#define SERVER_IP "127.0.0.1"
#define SERVER_PORT 2000
#define BUFFER_SIZE 1024

int main() {
    WSADATA wsaData;
    SOCKET clientSocket;
    struct sockaddr_in serverAddr;
    char sendBuffer[BUFFER_SIZE], receiveBuffer[BUFFER_SIZE];
    int result, serverAddrLen = sizeof(serverAddr);

    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        printf("WSAStartup failed\n");
        return 1;
    }

    // Create UDP socket
    clientSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
    if (clientSocket == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return 1;
    }

    // Set up the server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(SERVER_PORT);
    serverAddr.sin_addr.s_addr = inet_addr(SERVER_IP);

    // Communication loop
    while (1) {
        // Get message from user
        printf("Client: ");
        fgets(sendBuffer, sizeof(sendBuffer), stdin);
        sendBuffer[strcspn(sendBuffer, "\n")] = '\0';  // Remove trailing newline

        // Send message to server
        result = sendto(clientSocket, sendBuffer, strlen(sendBuffer), 0, 
                        (struct sockaddr *)&serverAddr, serverAddrLen);
        if (result == SOCKET_ERROR) {
            printf("Send failed\n");
            break;
        }

        // Receive response from server
        result = recvfrom(clientSocket, receiveBuffer, sizeof(receiveBuffer), 0,
                          (struct sockaddr *)&serverAddr, &serverAddrLen);
        if (result == SOCKET_ERROR) {
            printf("Receive failed\n");
            break;
        }
        receiveBuffer[result] = '\0';  // Null-terminate the received message
        printf("[Server]: %s\n", receiveBuffer);

        // Exit condition
        if (strcmp(sendBuffer, "exit") == 0 || strcmp(receiveBuffer, "exit") == 0)
            break;
    }

    // Cleanup
    closesocket(clientSocket);
    WSACleanup();

    return 0;
}
