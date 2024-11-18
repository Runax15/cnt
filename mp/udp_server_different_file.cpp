#include <iostream>
#include <fstream>
#include <winsock2.h>
#pragma comment(lib, "ws2_32.lib")


#define PORT 8080


int main() {
    WSADATA wsaData;
    SOCKET serverSock;
    struct sockaddr_in serverAddr, clientAddr;
    char buffer[1024];
    char filename[256];
    int clientAddrLen = sizeof(clientAddr), recvLen;


    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "Failed to initialize Winsock." << std::endl;
        return -1;
    }
    std::cout << "Winsock initialized successfully." << std::endl;


    // Create socket
    if ((serverSock = socket(AF_INET, SOCK_DGRAM, 0)) == INVALID_SOCKET) {
        std::cerr << "Socket creation failed." << std::endl;
        WSACleanup();
        return -1;
    }
    std::cout << "UDP socket created." << std::endl;


    // Prepare sockaddr_in structure
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    serverAddr.sin_addr.s_addr = INADDR_ANY;


    // Bind the socket
    if (bind(serverSock, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        std::cerr << "Bind failed with error code: " << WSAGetLastError() << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    std::cout << "Bind done on port " << PORT << "." << std::endl;


    // Receive the filename
    recvLen = recvfrom(serverSock, filename, sizeof(filename), 0, (struct sockaddr*)&clientAddr, &clientAddrLen);
    if (recvLen == SOCKET_ERROR) {
        std::cerr << "Failed to receive filename." << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    filename[recvLen] = '\0'; // Null-terminate the filename


    // Open the file to write the received data
    std::ofstream outputFile(filename, std::ios::binary);
    if (!outputFile) {
        std::cerr << "Failed to create file on server." << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    std::cout << "Receiving file: " << filename << std::endl;


    // Receive file data
    while (true) {
        recvLen = recvfrom(serverSock, buffer, sizeof(buffer), 0, (struct sockaddr*)&clientAddr, &clientAddrLen);
        if (recvLen == SOCKET_ERROR) {
            std::cerr << "Failed to receive file data." << std::endl;
            break;
        }
        if (recvLen == 0) {
            std::cout << "File transfer complete." << std::endl;
            break;
        }
        outputFile.write(buffer, recvLen);
    }


    // Cleanup
    outputFile.close();
    closesocket(serverSock);
    WSACleanup();
    return 0;
}


