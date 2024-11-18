#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define PORT 8000
#define BUFFER_SIZE 50

int main() {
    int clientsocket;
    struct sockaddr_in serveraddress;
    socklen_t serveraddress_len = sizeof(serveraddress);

    // Creating socket
    if ((clientsocket = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("Socket creation failed");
        return 1;
    }

    serveraddress.sin_family = AF_INET;
    serveraddress.sin_port = htons(PORT);
    serveraddress.sin_addr.s_addr = inet_addr("127.0.0.1");

    // Sending message to the server
    char a[BUFFER_SIZE];
    printf("Enter your message: ");
    if (fgets(a, BUFFER_SIZE, stdin) != NULL) {
        // Remove newline character if present
        a[strcspn(a, "\n")] = '\0';
        
        if (sendto(clientsocket, a, strlen(a), 0, (struct sockaddr *)&serveraddress, serveraddress_len) < 0) {
            perror("Send failed");
            close(clientsocket);
            return 1;
        }
    } else {
        perror("Input failed");
        close(clientsocket);
        return 1;
    }

    // Receiving response from the server
    char response[BUFFER_SIZE] = {0};
    int bytes_received = recvfrom(clientsocket, response, sizeof(response) - 1, 0, (struct sockaddr *)&serveraddress, &serveraddress_len);
    if (bytes_received < 0) {
        perror("Receive failed");
    } else {
        response[bytes_received] = '\0';  // Ensure null-termination
        printf("Server response: %s\n", response);
    }

    // Closing the socket
    close(clientsocket);

    return 0;
}
