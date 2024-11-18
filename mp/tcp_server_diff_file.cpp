#include <iostream>
#include <fstream>
#include <winsock2.h>
#pragma comment(lib, "ws2_32.lib") // Link with ws2_32.lib


#define PORT 8080


int main() {
    WSADATA wsaData;
    SOCKET serverSock, clientSock;
    struct sockaddr_in serverAddr, clientAddr;
    int clientAddrSize = sizeof(clientAddr);
    char buffer[1024];
    char filename[256]; // Buffer to store the received filename


    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "Failed to initialize Winsock." << std::endl;
        return -1;
    }
    std::cout << "Winsock initialized successfully." << std::endl;


    // Create socket
    if ((serverSock = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET) {
        std::cerr << "Socket creation failed." << std::endl;
        WSACleanup();
        return -1;
    }
    std::cout << "TCP socket created." << std::endl;


    // Prepare sockaddr_in structure
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    serverAddr.sin_addr.s_addr = INADDR_ANY;


    // Bind socket
    if (bind(serverSock, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == SOCKET_ERROR) {
        std::cerr << "Bind failed." << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    std::cout << "Socket bound to port " << PORT << std::endl;


    // Listen for incoming connections
    if (listen(serverSock, 3) == SOCKET_ERROR) {
        std::cerr << "Listen failed." << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    std::cout << "Listening for incoming connections..." << std::endl;


    // Accept an incoming connection
    if ((clientSock = accept(serverSock, (struct sockaddr*)&clientAddr, &clientAddrSize)) == INVALID_SOCKET) {
        std::cerr << "Connection acceptance failed." << std::endl;
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }
    std::cout << "Connection accepted." << std::endl;


    // Receive the filename first
    recv(clientSock, filename, sizeof(filename), 0);
    std::cout << "Receiving file: " << filename << std::endl;


    // Open file to save received data
    std::ofstream outputFile(filename, std::ios::binary);
    if (!outputFile) {
        std::cerr << "Failed to open file for writing." << std::endl;
        closesocket(clientSock);
        closesocket(serverSock);
        WSACleanup();
        return -1;
    }


    // Receive file data
    int bytesReceived;
    while ((bytesReceived = recv(clientSock, buffer, sizeof(buffer), 0)) > 0) {
        outputFile.write(buffer, bytesReceived);
    }
    std::cout << "File received and saved as " << filename << std::endl;


    // Cleanup
    outputFile.close();
    closesocket(clientSock);
    closesocket(serverSock);
    WSACleanup();
    return 0;
}

