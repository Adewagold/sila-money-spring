# Sila Spring Java
This is a spring java application to integrate to sila ethereum platform. 

## Getting Started  
* Clone the application repo and start the application.  
* git clone https://github.com/Adewagold/sila-spring-java.git** and start the application. You can configure the port in the `application.properties` file to use your desired port number, the default port number used in this app is 8090.  
* Modify server port: `server.port=8090`
---

## Sila Configuration  
Sila api version `sila.app.version=0.2`  
Crypto supported `sila.crypto.type=ETH`  
Timezone `sila.time.zone=GMT+1` 

## Url Configuration
The following properties are the sila url confiuration properties located in your application.properties file. 
* Base url: `sila.base.url=https://sandbox.silamoney.com/${sila.app.version}/`  
* Check Handle: `sila.check.handle=${sila.base.url}check_handle`  
* Register: `sila.register=${sila.base.url}register`  
* Request Kyc: `sila.request_kyc=${sila.base.url}register`  
* Check Kyc: `sila.check.kyc=${sila.base.url}register`  
* Link Account: `sila.link.account=${sila.base.url}register`  
* Get Account: `sila.get.account=${sila.base.url}get_account`  
* Issue Sila: `sila.issue.sila=${sila.base.url}issue_sila`  
* Transfer Sila: `sila.transfer.sila=${sila.base.url}transfer_sila`  
* Redeem Sila: `sila.redeem.sila=${sila.base.url}redeem_sila`  
* Get Transactions: `sila.get_transactions=${sila.base.url}get_transactions`  
* Sila Balance: `sila.get.balance=${sila.base.url}silaBalance`
 
 ## Check Handle (Request/Response)  
 **Endpoint:** http:localhost:8080/api/v1/check_handle  
 **Request method:** POST  
 **Request**: > `{
                "user_handle":"adewale.silamoney.eth",
                "message":"header_msg"
                }`  
**Response**: > `{
                      "status": "SUCCESS",
                      "message": "adewale is available!",
                      "reference": "e55444ef-8cb2-45dc-9ef1-02d3286fd421"
                  }`
 
 ## Request Kyc (Request/Response)  
  **Endpoint:** http:localhost:8080/api/v1/check_handle  
  **Request method:** POST  
  **Request**: > `{
                 "user_handle":"adewale",
                 "message":"header_msg"
                 }`  
 **Response**: > `{
                      "status": "SUCCESS",
                      "message": "ayokanmi submitted for KYC review.",
                      "reference": "63efded3-758f-43a7-810b-79c36e3d2028",
                      "external_request": "false"
                  }`
  
   ## Check KYC (Request/Response)  
    **Endpoint:** http:localhost:8080/api/v1/check_handle  
    **Request method:** POST  
    **Request**: > `{
                   "user_handle":"adewale",
                   "message":"header_msg"
                   }`  
   **Response**: > `{
                        "status": "SUCCESS",
                        "message": "ayokanmi has passed ID verification!",
                        "reference": "a977f063-4012-46f1-9665-6bbbce303342",
                        "external_request": null
                    }`

   ## Register  (Request/Response)  
    **Endpoint:** http:localhost:8080/api/v1/check_handle  
    **Request method:** POST  
    **Request**: > `{
                    "user_handle":"ayokanmi",
                    "message":"entity_msg",
                    "address": {
                        "address_alias": "home",
                        "street_address_1": "123 Main Street",
                        "city": "New City",
                        "state": "OR",
                        "country": "US",
                        "postal_code": "97204-1234"
                      },
                      "identity": {
                        "identity_alias": "SSN",
                        "identity_value": "123452222"
                      },
                      "contact": {
                        "phone": "503-123-4567",
                        "contact_alias": "",
                        "email": "example@silamoney.com"
                      },
                      "crypto_entry": {
                        "crypto_alias": "Address 1",
                        "crypto_address": "0x8703Aa649Be346E800b0696Bb3103CF268Cb35b7",
                        "crypto_code": "ETH"
                      },
                      "entity": {
                        "birthdate": "1900-01-31",
                        "entity_name": "Example User",
                        "first_name": "Example",
                        "last_name": "User",
                        "relationship": "user"
                      }
                    }`  
   **Response**: > `{
                        "status": "SUCCESS",
                        "message": "ayokanmi was successfully registered.",
                        "reference": "8e669b4a-fe47-419d-83c1-1aed014473ef"
                    }`

   ## Link Account  (Request/Response)  
    **Endpoint:** http:localhost:8080/api/v1/link_account  
    **Request method:** POST  
    **Request**: > `{
                    "user_handle":"ayokanmi",
                    "message":"link_account_msg",
                    "public_token": "public-sandbox-fee31f93-688b-4a2e-b9c4-5234a65805c5",
                    "account_name": "Chase Checking Account"
                    }`  
   **Response**: > `{
                        "status": "SUCCESS"
                    }`
         