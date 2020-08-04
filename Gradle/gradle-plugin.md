# Gradle 插件
## 用处
* 扩展Gradle模型 
* 根据约定配置项目
* 应用特殊配置

### 插件 vs 构建脚本
* 多项目之间重用
* 高度模块化
* 封装必须的逻辑，使构建脚本专注于声明

## 插件类型

### 二进制插件
继承Plugin接口编程或者使用Gradle DSL语言来声明。

#### 通过`plugins DSL`使用插件
```
plugins {
	id <<plugin id>> //使用核心插件, e.g. java
	id <<plugin id>> version <<plugin version>> [apply false]
}
```
#### `plugins{}`块的限制
1. `plugins{}`里面的`<<plugin id>>`和`<<plugin version>>`必须是常数，文字或字符串，不能带变量（需要用变量的话，使用`plugin version management`）
2. `plugins{}`里面不能有其他声明，会报编译错误
3. `plugins{}`必须在脚本的顶层声明 
4. `plugins{}`只能在`build`脚本以及`setting.gradle`里面使用，不能在脚本插件以及初始化脚本中使用

#### 在多个子项目中使用插件
在`root`/`mater`项目的`plugins{}`模块设置`apply false`,在`subprojects{}`块使用`apply plugin`或者在子项目的构建脚本使用`plugins{}`块。

#### 使用`buildSrc`目录下的插件
##### 在`buildSrc`中给插件绑定ID
**buildSrc/build.gradle**
```
plugins {
	id 'java-gradle-plugin
}
gradlePlugin {
	plugins {
		myPlugins {
			id = 'my-Plugin' // 绑定的ID，这样在build.gradle中直接使用ID应用插件
			implementationClass = 'my.MyPlugin' // 实现插件的类
		}
	}
}
```
#### 插件管理`Plugin Management`
`pluginManagement{}`块只在`setting.gradle`的最开始或者初始化脚本出现。
**settings.gradle**
```
pluginManagement {
    plugins {
		// 定义插件版本，这样可以在build.gradle中直接使用ID应用插件
		id 'com.example.hello' version "${helloPluginVersion}" // 支持从其他文件定义的版本号
	}
	resolutionStrategy {
		// 定义插件解析规则
		eachPlugin {
			if (request.id.namespace == 'com.example') {
				useModule('com.example:sample-plugin:1.0.0')
			}
		}
	}
	repositories {
		// 定义插件仓库
		mavan {
			url '../maven-repo' //定义获取插件的私有maven仓库
		}
		gradlePluginPortal()
		ivy {
			url '../ivy-repo' //定义获取插件的私有ivy仓库
		}
	}
}
```
**gradle.properties**
`helloPluginVersion=1.0.0`
#### 通过传统方式使用插件
**build.gradle**
`apply plugin: 'java'`
or
`apply plugin: 'JavaPlugin'`
##### 利用`buildscript`块使用插件
**build.gradle**
```
buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:0.4.1'
	}
}
apply plugin: 'com.jfrog.bintray'
```
### 脚本插件
#### 使用脚本插件
**build.gradle**
`apply from：'other.gradle'`