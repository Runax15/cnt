//Compile - gcc ClientTCP.c -o ClientTCP.exe -lws2_32
// Run - ClientTCP.exe
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <winsock2.h>
#include <ws2tcpip.h>

#pragma comment(lib, "ws2_32.lib")

int main() {
    WSADATA wsaData;
    SOCKET clientSocket;
    struct sockaddr_in serverAddr;
    char receiveBuffer[100] = "", sendBuffer[100] = "";
    int result;

    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        printf("WSAStartup failed\n");
        return 1;
    }

    // Create socket
    clientSocket = socket(AF_INET, SOCK_STREAM, 0);
    if (clientSocket == INVALID_SOCKET) {
        printf("Socket creation failed\n");
        WSACleanup();
        return 1;
    }

    // Define server address
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(2000);  // Convert to network byte order
    serverAddr.sin_addr.s_addr = inet_addr("127.0.0.1");

    // Connect to server
    result = connect(clientSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr));
    if (result == SOCKET_ERROR) {
        printf("Connection failed\n");
        closesocket(clientSocket);
        WSACleanup();
        return 1;
    }

    // Communication loop
    while (1) {
        printf("\nClient: ");
        fgets(sendBuffer, sizeof(sendBuffer), stdin);  // Get input from user
        sendBuffer[strcspn(sendBuffer, "\n")] = '\0';  // Remove trailing newline

        send(clientSocket, sendBuffer, strlen(sendBuffer), 0);  // Send message

        if (strcmp(sendBuffer, "end") == 0)
            break;

        result = recv(clientSocket, receiveBuffer, sizeof(receiveBuffer) - 1, 0);  // Receive response
        if (result == SOCKET_ERROR) {
            printf("Receive failed\n");
            break;
        }

        receiveBuffer[result] = '\0';  // Null-terminate the received data
        printf("Server: %s", receiveBuffer);  // Display server response

        if (strcmp(receiveBuffer, "end") == 0)
            break;
    }

    // Cleanup
    closesocket(clientSocket);
    WSACleanup();

    return 0;
}
