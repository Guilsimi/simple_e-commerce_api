### Generating Asymmetric Key Pair

First, verify Keytool is installed in your system. Keytool is a keys and certificate management tool bundle with the Jdk.<br>
Run `keytool` command in your **ec-oauth** microservice terminal. If a list of commands doesn't appear, recheck your Jdk installation. If everything is correct, generate the keys using the following command:

_(Obs: Save these variable for the config-server)_

```bash
keytool -genkeypair -alias alias_name -keyalg RSA -keypass key_password -keystore src/main/resources/keystore/keystore_name.jks -storepass keystore_password -validity days
```

After execution, the terminal will prompt you to enter additional information (optional step).
