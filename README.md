#### Test yandex search and yandex authorization without Selenium
##### Precondition
- `JDK 1.8.+` is installed and properly configured in your system
- `Maven 3.5.+` is installed and properly configured in your system
- `Lombok plugin` in case you use IntelliJIDEA
##### How to run tests
- Unpack archive to some folder
- From folder root execute the following command:
 **mvn clean test**
- Test execution should be started
##### Test report
- After test execution is completed you can find test report by the following path: 
**<root_folder>\target\surefire-reports\html\index.html**