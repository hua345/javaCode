# 加解密

- [https://github.com/looly/hutool](https://github.com/looly/hutool)
- [https://github.com/brix/crypto-js](https://github.com/brix/crypto-js)
- [https://hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0](https://hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0)

`AES`,高级加密标准（`Advanced Encryption Standard`，缩写：AES），在密码学中又称`Rijndael`加密法，是美国联邦政府采用的一种区块加密标准。

严格地说，`AES`和`Rijndael`加密法并不完全一样（虽然在实际应用中二者可以互换），因为`Rijndael`加密法可以支持更大范围的区块和密钥长度：AES的区块长度固定为128
比如: 密钥长度则可以是128，192或256比特；而Rijndael使用的密钥和区块长度可以是32位的整数倍，以128位为下限，256比特为上限。加密过程中使用的密钥是由Rijndael密钥生成方案产生。

CBC模式自设定秘钥,需要设置偏移量

## 引入依赖

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-crypto</artifactId>
    <version>5.5.2</version>
</dependency>
```

```bash
npm install crypto-js
```
