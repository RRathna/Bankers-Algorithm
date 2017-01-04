# Bankers-Algorithm
Implemented multi-threaded resource sharing system using Banker's algorithm to check availability and allocate resources

By default there are 3 types of resource. When running the code, the maximum number of units in each resource type needs to be specified as command line arguement
Eg:  
    >java BankersAlgorithm 10 10 10

The consumer threads are generated automatically and resources are allocated only if all the three resources are available in the required numbers.
