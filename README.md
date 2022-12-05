# SimpleFTPReplica_MultiThreaded
FTP protocol is a protocol that is used to transfer files from one machine to another. In this problem, you are asked to implement in Java a client and a server for a simpler version of the FTP protocol with only three commands in Java. The three commands are:

1. get <remote_filename>: Copy the file with the given filename from server(remote) directory to client(local) directory. 
2. ls: List the files and the name of the subdirectories in the server(remote) directory.
3. cd <directory_name>: Change the server(remote) directory of the current directory to the provided directory name.

This simple FTP server performs data transfer on a different channel than the control channel, aka, concurrent transfer.
