#include <iostream>
#include <fstream>
#include <winsock2.h>
#pragma comment(lib, "ws2_32.lib")


#define PORT 8080


int main() {
    WSADATA wsaData;
    SOCKET clientSock;
    struct sockaddr_in serverAddr;
    char buffer[1024];
    char filename[256];


    // Initialize Winsock
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "Failed to initialize Winsock." << std::endl;
        return -1;
    }
    std::cout << "Winsock initialized successfully." << std::endl;


    // Create socket
    if ((clientSock = socket(AF_INET, SOCK_DGRAM, 0)) == INVALID_SOCKET) {
        std::cerr << "Socket creation failed." << std::endl;
        WSACleanup();
        return -1;
    }
    std::cout << "UDP socket created." << std::endl;


    // Prepare sockaddr_in structure
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(PORT);
    serverAddr.sin_addr.s_addr = inet_addr("192.168.1.118"); // Replace with the server's IP address


    // Enter the filename
    std::cout << "Enter the filename to send: ";
    std::cin.getline(filename, sizeof(filename));


    // Send the filename first
    sendto(clientSock, filename, sizeof(filename), 0, (struct sockaddr*)&serverAddr, sizeof(serverAddr));


    // Open the file to send
    std::ifstream inputFile(filename, std::ios::binary);
    if (!inputFile) {
        std::cerr << "Failed to open file." << std::endl;
        closesocket(clientSock);
        WSACleanup();
        return -1;
    }


    // Send file data
    while (!inputFile.eof()) {
        inputFile.read(buffer, sizeof(buffer));
        sendto(clientSock, buffer, inputFile.gcount(), 0, (struct sockaddr*)&serverAddr, sizeof(serverAddr));
    }
    std::cout << "File sent successfully." << std::endl;


    // Cleanup
    inputFile.close();
    closesocket(clientSock);
    WSACleanup();
    return 0;
}
