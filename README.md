Spring Security project that uses JWT token for authentication and authorization. User information including their roles are stored in postgres db. 
We use "/signin" endpoint with username and password to retrive JWT token, then this token can be used for authentication(until it expires).
