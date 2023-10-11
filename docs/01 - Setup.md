##   Ensuring the Correct JDK Version


Below you'll find a step-by-step guide to ensure you're using JDK 21, particularly the GraalVM version `java 21-graalce`.
If you prefer to use a different JDK 21 version or installation method, feel free to do so. However, please note that the configuration of IntelliJ IDEA may differ from the steps below. 
When having issues with the configuration, please use step 2 to ensure you're using the correct JDK version in the IDE.
---

Ensuring you're using the correct JDK version is crucial for the stability and functionality of your application, especially when leveraging features from newer Java versions. Here's a step-by-step guide to ensure you're using JDK 21, particularly the GraalVM version `java 21-graalce`.

### **1. Installing the Latest JDK Version**:

#### **For macOS Users (using SDKMAN!):**

SDKMAN! is a versatile tool that simplifies the process of installing and managing multiple software development kits on macOS and Linux.

1. **Install SDKMAN!** (if not already installed):
    
    bashCopy code
    
    `curl -s "https://get.sdkman.io" | bash source "$HOME/.sdkman/bin/sdkman-init.sh"`
    
2. **Install JDK 21 with GraalVM**:
    
    bashCopy code
    
    `sdk install java 21-graalce`
    
3. **Set it as the default** (optional):
    
    bashCopy code
    
    `sdk default java 21-graalce`
    

#### **For Windows Users**:
  *Often IntelliJ on Windows will already recognize the project and structure. It'll prompt you for installation and configuration options when required.*
  Manual steps;
  https://www.oracle.com/nl/java/technologies/downloads/#graalvmjava21-windows
  Download the following file from Oracle and unpack this within a location on your machine. Copy the location and paste this in your **PATH** environment variables. 
  This should suffice to let IntelliJ pick with the configuration and install any additional requirements.

  
---

### **2. Configuring IntelliJ IDEA**:

Once you've installed the desired JDK version, you need to configure IntelliJ IDEA to use it.

1. **Open IntelliJ IDEA** and navigate to your project.
    
2. **Access Project Settings**:
    
    - Click on `File` -> `Project Structure` (or press `Ctrl+Alt+Shift+S` on Windows/Linux, `Cmd+;` on macOS).
3. **Set up the SDK**:
    
    - In the `Project` tab, click on `New...` next to the `Project SDK` dropdown.
    - Navigate to the location where `java 21-graalce` is installed. For SDKMAN! users, it's typically in `~/.sdkman/candidates/java/21-graalce`.
    - Select the directory and click `OK`.
4. **Set Language Level**:
    
    - In the same `Project` tab, set the `Project language level` dropdown to a level higher than 20. Ensure you enable the preview features by selecting a level with "Preview" in its name.
5. **Apply and Close**:
    
    - Click `Apply` and then `OK` to save the changes.
6. **For the Kotlin users**
    - Set the compiler version to the beta channel in languages &frameworks > Kotlin > update channel 
    - You can find this in the settings of IntelliJ IDEA.
7. **Invalidate Caches (if necessary)**: If you encounter any issues or if IntelliJ doesn't recognize the new JDK, it might help to invalidate caches and restart.
    
    - Click on `File` -> `Invalidate Caches / Restart...`.
    - Choose `Invalidate and Restart`.


For Kotlin you may need to extend version to run the Beta release instead of 'Stable' release.
IntelliJ settings > Kotlin > 'Update channel' > 'Early access preview'.
Restart IntelliJ and invalidate caches. 

---
 ### Installing K6
 Please follow the installation steps on https://k6.io/docs/get-started/installation/
 