# Employee Manager

### Api basic functionality:
* Read an Excel document
* Map data to POJO-s
* Create entity relations
    - Created tables for: User, UserDetails, Address, Department
* Fill missing data
    - Some users had departments that were not in the department list, so I found the departments that were not in the departments list, assigned a random user as department leader and saved them in the database.
* Store data in the database
* Bonus task:
    - Assume that the employees.xlsx gets reimported daily with respective updates (new users added, existing users removed, values for existing users changed). Rework your import logic to respect these changes and update the database accordingly on every import.
    - As of my understanding the exact same copy of the xlsx file is to be made every time that file gets re-imported, instead of comparing each field in the database and checking it for differences, I decided to rewrite all the data on every file update.
### How to test:
* Create a postgresql database with the name: 'employeemanager'
    - Change the postgresql username and password in the application.yml file default(usr: postgres, pwd: root)
* Run the application, hibernate will generate all tables with the necessary relations, no table scripts are needed.
* In this GitHub repository you'll find a postman collection that you can open using postman and call the endpoints.
    - In your postman environment create a variable 'host' with value of 'http://localhost:8080' or replace the {{host}} with http://localhost:8080 in the url section in postman.

### Endpoints
* Upload File
    - Description
        - Upload xlsx file
    - Endpoint
        - POST: {{host}}/api/employee/upload
        - Body
          - Type: from-data
            - key: xlsxFile
            - value: base64 of xlsx file
 * Search
    - Description
        - Search users by: first name, last name, first and last name and by email
    - Endpoint
        - GET: {{host}}/api/employee/search/{john}
    -Body
        - Type: Request Param
* Active and Inactive
    - Description
        - Get users grouped by state
    - Endpoint
        - GET: {{host}}/api/employee/active-inactive
* Sort in Ascending order
    - Description
        - List users in ascending order by first and last name
    - Endpoint
        - GET: {{host}}/api/employee/users-asc-order
* Group By Department
    - Description
        - List users grouped by departments
    - Endpoint
        - GET: {{host}}/api/employee/grouped-by-department
