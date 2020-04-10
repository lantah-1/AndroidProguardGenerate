这是一份Android混淆的字典文件，可以在**ProguardGenerateClass**中生成你认为比较乱的一些规则。

**comm_dict.txt**文件是网上的比较通用的字典文件，在此基础上，我在里面添加了一些其他的字符，使得更加的乱。

**output_dict.txt**是生成输出的文件，可以直接使用。

#### 使用
在我们的混淆文件中，添加以下规则即可：
```
-obfuscationdictionary output_dict.txt
-classobfuscationdictionary output_dict.txt
-packageobfuscationdictionary output_dict.txt
```