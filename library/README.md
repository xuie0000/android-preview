
# 部署依赖至服务器

- 在`local.progerties`中添加user-key
```
bintray.user=xuie0000
bintray.apikey=<your api key>
```

- `gradlew install`
- `gradlew bintrayUpload`

> **Windows10用命令执行，窗口点有BUG**，其他系统还没有试

