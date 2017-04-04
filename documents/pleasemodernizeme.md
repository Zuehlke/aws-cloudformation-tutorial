## How to define custom parameters?

Here we define that the name of an existing EC2 KeyPair to enable SSH access to the instances, the database's name, database's username and database's password should be passed as parameter when we create the stack.

Those variables are either handed over by JSON (separate file) or just like this over the command line.

```json
{
	...
	 "Parameters": {
		"KeyName": {
		  "Description": "Name of an existing EC2 KeyPair to enable SSH access to the instances",
			"Type": "AWS::EC2::KeyPair::KeyName"
		},
       "DBName": {
	      "Description": "Enter the name of the database",
	      "Type": "String",
	      "AllowedPattern": "[a-zA-Z]*"
      },
      "DBUser" : {
	      "Description" : "The database admin username",
	      "Type" : "String",
	      "MinLength" : "1",
         "MaxLength" : "41",
         "AllowedPattern" : "[a-zA-Z0-9]*"
      },
      "DBPwd" : {
        "NoEcho" : "true",
	       "Description" : "The database admin password",
	       "Type" : "String",
	       "MinLength" : "8",
	       "MaxLength" : "41",
	       "AllowedPattern" : "[a-zA-Z0-9]*"
      }
	},
	...
}
```