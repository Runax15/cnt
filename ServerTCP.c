//Compile - gcc ServerTCP.c -o ServerTCP.exe -lws2_32
// Run - ServerTCP.exe
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

int main() {
    WSADATA wsaData;
    SOCKET listenSocket, clientSocket;
    struct sockaddr_in serverAddr, clientAddr;
    int addrLen, result;
    char receiveBuffer[100] = "", sendBuffer[100] = "";

    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        printf("WSAStartup failed\n");
        return 1;
    }

    // Create socket
    listenSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (listenSocket == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return 1;
    }

    // Define server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(2000);  // Convert port to network byte order
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");

    // Bind socket
    if (bind(listenSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        printf("Bind failed\n");
        closesocket(listenSocket);
        WSACleanup();
        return 1;
    }

    // Listen for incoming connections
    if (listen(listenSocket, 1) == SOCKET_ERROR) {
        printf("Listen failed\n");
        closesocket(listenSocket);
        WSACleanup();
        return 1;
    }

    // Accept a client connection
    addrLen = sizeof(clientAddr);
    clientSocket = accept(listenSocket, (struct sockaddr *)&clientAddr, &addrLen);
    if (clientSocket == INVALID_SOCKET) {
        printf("Accept failed\n");
        closesocket(listenSocket);
        WSACleanup();
        return 1;
    }

    // Communication loop
    while (1) {
        int recvLen = recv(clientSocket, receiveBuffer, sizeof(receiveBuffer) - 1, 0);
        if (recvLen == SOCKET_ERROR) {
            printf("Receive failed\n");
            break;
        }

        receiveBuffer[recvLen] = '\0';  // Null-terminate received data
        printf("\nClient: %s", receiveBuffer);

        if (strcmp(receiveBuffer, "exit") == 0)
            break;

        // Get server's reply
        printf("\nServer: ");
        fgets(sendBuffer, sizeof(sendBuffer), stdin);  // Get input from user
        sendBuffer[strcspn(sendBuffer, "\n")] = '\0';  // Remove trailing newline

        send(clientSocket, sendBuffer, strlen(sendBuffer), 0);  // Send server's reply
        if (strcmp(sendBuffer, "exit") == 0)
            break;
    }

    // Cleanup
    closesocket(clientSocket);
    closesocket(listenSocket);
    WSACleanup();

    return 0;
}
