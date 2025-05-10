@echo off
REM Root Project Directory
mkdir MultiVendorApp
cd MultiVendorApp

REM Main Java Source
mkdir src\main\java\com\multivendor\config
mkdir src\main\java\com\multivendor\controller
mkdir src\main\java\com\multivendor\model
mkdir src\main\java\com\multivendor\repository
mkdir src\main\java\com\multivendor\service

REM Resources
mkdir src\main\resources

REM Test Directory
mkdir src\test\java\com\multivendor

REM Create main class
type nul > src\main\java\com\multivendor\MultiVendorApplication.java

REM Config
type nul > src\main\java\com\multivendor\config\SecurityConfig.java

REM Controllers
type nul > src\main\java\com\multivendor\controller\OrderController.java
type nul > src\main\java\com\multivendor\controller\VendorController.java

REM Models
type nul > src\main\java\com\multivendor\model\Customer.java
type nul > src\main\java\com\multivendor\model\Order.java
type nul > src\main\java\com\multivendor\model\OrderDetail.java
type nul > src\main\java\com\multivendor\model\OrderLineStatus.java
type nul > src\main\java\com\multivendor\model\OrderLineStatusLog.java
type nul > src\main\java\com\multivendor\model\Product.java
type nul > src\main\java\com\multivendor\model\Vendor.java
type nul > src\main\java\com\multivendor\model\VendorCategoryCommission.java
type nul > src\main\java\com\multivendor\model\VendorCommission.java
type nul > src\main\java\com\multivendor\model\VendorCommissionLog.java
type nul > src\main\java\com\multivendor\model\VendorOrderDetail.java
type nul > src\main\java\com\multivendor\model\VendorPayment.java
type nul > src\main\java\com\multivendor\model\VendorTransaction.java

REM Repositories
type nul > src\main\java\com\multivendor\repository\CustomerRepository.java
type nul > src\main\java\com\multivendor\repository\OrderDetailRepository.java
type nul > src\main\java\com\multivendor\repository\OrderLineStatusLogRepository.java
type nul > src\main\java\com\multivendor\repository\OrderLineStatusRepository.java
type nul > src\main\java\com\multivendor\repository\OrderRepository.java
type nul > src\main\java\com\multivendor\repository\ProductRepository.java
type nul > src\main\java\com\multivendor\repository\VendorCategoryCommissionRepository.java
type nul > src\main\java\com\multivendor\repository\VendorCommissionLogRepository.java
type nul > src\main\java\com\multivendor\repository\VendorCommissionRepository.java
type nul > src\main\java\com\multivendor\repository\VendorOrderDetailRepository.java
type nul > src\main\java\com\multivendor\repository\VendorPaymentRepository.java
type nul > src\main\java\com\multivendor\repository\VendorRepository.java
type nul > src\main\java\com\multivendor\repository\VendorTransactionRepository.java

REM Services
type nul > src\main\java\com\multivendor\service\CommissionService.java
type nul > src\main\java\com\multivendor\service\CustomerService.java
type nul > src\main\java\com\multivendor\service\OrderProcessingService.java
type nul > src\main\java\com\multivendor\service\PaymentService.java
type nul > src\main\java\com\multivendor\service\VendorService.java

REM Resources
type nul > src\main\resources\application.properties

REM Project Build File Placeholder
type nul > pom.xml
