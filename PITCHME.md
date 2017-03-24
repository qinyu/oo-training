
# 面向对象设计原则

❓有哪些面向对象编程的概念 <!-- .element: class="fragment" -->

---

## 面向对象术语

* **封装**-把一个单元的内部实现隐藏起来，显示尽量少的接口，使变化局限于单元内部 
* **继承**-使用现有类的一种方式，子类可以直接复用父类的属性和行为 
* **代理**-使用现有类的另一种方式，将请求转派给已有类的对象 
* **多态**-不同类型的对象能响应同一个方法调用，并表现出各自不同的行为 

❓重要程度如何排序 <!-- .element: class="fragment" -->

<!--
封装->委托->继承
继承的问题
-->

---

# 应对变化和复杂

---

# 高内聚&低耦合

* **内聚**是一个系统里多个部分一起执行工作的度量，来获得比每个部分单独工作获得更好的结果 
* **耦合**是一个类、方法或者任何一个实体直接与另一个实体连接的度 

---

# SOLID原则 
[《敏捷软件开发 原则、模式与实践》](https://book.douban.com/subject/1140457/)     
<img src="https://img3.doubanio.com/lpic/s1671095.jpg" style="width: 250px;"/>    
作者: Robert C. Martin   
[✨一些软件设计的原则](http://coolshell.cn/articles/4535.html) <!-- .element: class="fragment" -->

+++

## 单一职责原则(Single Responsibility)

一个类只能因为一个原因改变

<!--
代码重复，不同“方向”的变化
-->

+++

## 开闭原则(Open/Close)

软件实体(类、方法，对象)对修改封闭，对扩展开放

<!--
面向接口编程，组合由于继承(LSP)
-->

+++

## 里氏替换原则(Liskov's Substitution)

子类不能破坏父类的类型定义

<!--
继承关系的开闭原则，契约
-->

+++

## 接口隔离原则(Interface Segregation)

客户不应该被强迫依赖它用不上的方法

+++

## 依赖倒置原则(Dependency Inversion)

A. 上层模块不应该依赖下层模块，两者之间应该依赖抽象而不是具体实现
B. 抽象不应该依赖于细节，细节应该依赖于抽象

---

## 设计模式
[《设计模式：可复用面向对象软件的基础》](https://book.douban.com/subject/1052241/)  
<img src="https://img3.doubanio.com/lpic/s1074361.jpg" style="width: 250px;"/>     
作者(GoF): Gamma Erich, Helm Richard, Johnson Ralph, Vlissides John.

---

## 设计模式分类

![](http://www.vincehuston.org/images/GoF_full_medium.png)  

**创建型模式**<!-- .element: class="fragment" --> **行为型模式**<!-- .element: class="fragment" --> **结构型模式**<!-- .element: class="fragment" --> **架构模式**<!-- .element: class="fragment" -->

---

## 创建型模式

* 单例模式
* 工厂方法模式
* 抽象工厂模式
* 建造者模式
* 原型模式

---

# 单例模式

+++

## 实现

```java
public class ToolManager {
    private static volatile ToolManager mToolManager;
    private static Object mObject = new Object();

    public static ToolManager getInstance(){
        if (mToolManager == null){
            synchronized (mObject) {
                if (mToolManager == null) {
                    mToolManager = new ToolManager();
                }
            }
        }
        return mToolManager;
    }
}
```

❓为什么要这样实现   <!-- .element: class="fragment" -->
<!--延迟加载的线程不安全，synchronized带来性能开销，双重检查锁定必须使用volatile(java 1.5)
-->  
[双重检查锁定与延迟初始化](http://www.infoq.com/cn/articles/double-checked-locking-with-delay-initialization) <!-- .element: class="fragment" -->

+++

## 最短的线程安全的实现
```java
public class InstanceFactory {
    private static class InstanceHolder {
        public static Instance instance = new Instance();
    }

    public static Instance getInstance() {
        return InstanceHolder.instance;
    }
}
```

+++

## 运用

[👿为什么单例模式是邪恶的](http://www.cnblogs.com/nomoneynowife/p/3719031.html)  <!-- .element: class="fragment" -->
* 和全局变量没什么区别<!-- .element: class="fragment" --> 可能导致内存泄漏 <!-- .element: class="fragment" -->   
* 即负责创建逻辑也限制创建的数量，违反SRP<!-- .element: class="fragment" --> 
<!--工厂模式与建造者模式不违反SRP-->
* 调用的地方紧耦合，违反OCP<!-- .element: class="fragment" --> 无法在测试时方便的替换 <!-- .element: class="fragment" -->
* 全局状态处于未知状态<!-- .element: class="fragment" --> 测试无法独立运行 <!-- .element: class="fragment" -->

✨仔细分析上下文，确定无上诉设计的需要(确定是全局仅需要一个实例); 或者改用其他设计模式 <!-- .element: class="fragment" -->

---

# 简单工厂模式

把创建分离到单独的方法

+++ 

## 实现(待续)
```java
interface Door {
    public float getWidth();
    public float getHeight();
}

class WoodenDoor implements Door {
    protected float width;
    protected float height;

    public WoodenDoor(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
```

+++

## 实现(完)
```java
class DoorFactory {
   public static Door makeDoor(width, height) {
       return new WoodenDoor(width, height);
   }
}
Door door = DoorFactory.makeDoor(100, 200);
```

+++

## 运用

创建接口的实例，隐藏具体的接口实现

IDE重构: https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-factory-method.html

---

# 工厂方法模式

把生成逻辑移交给子类

+++

## 实现(待续)

```java
interface Interviewer {
    public void askQuestions();
}

public class Developer implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about design patterns!");
    }
}

public class CommunityExecutive implements Interviewer {
    public void askQuestions() {
        System.out.prinln("Asking about community building");
    }
}
```

+++

## 实现(待续)

```java
public abstract class HiringManager {

    // 工厂方法(抽象)
    abstract public Interviewer makeInterviewer();

    public void takeInterview() {
        Interviewer interviewer = this.makeInterviewer();
        interviewer.askQuestions();
    }
}
```
+++


## 实现(待续)

```java
public class DevelopmentManager extends HiringManager {
    public Interviewer makeInterviewer() {
        return new Developer();
    }
}

public class MarketingManager extends HiringManager {
    public Interviewer makeInterviewer() {
        return new CommunityExecutive();
    }
}
```

+++

## 实现(完)

```java
DevelopmentManager devManager = new DevelopmentManager();
devManager.takeInterview(); // 输出: Asking about design patterns

MarketingManager marketingManager = new MarketingManager();
marketingManager.takeInterview(); // 输出: Asking about community building.

```
+++

## 运用

工厂本身也是抽象的

---

# 抽象工厂模式

一个制造工厂的工厂；一个工厂把独立但是相关／有依赖性的工厂进行分类，但是不需要给出具体的类。

+++

## 实现(待续)
```java
interface Door {
    public void getDescription();
}

public class WoodenDoor implements Door {
    public void getDescription() {
        System.out.println("I am a wooden door");
    }
}

class IronDoor implements Door {
    public void getDescription() {
        System.out.println("I am an iron door");
    }
}
```

+++

## 实现(待续)
```java
interface DoorFittingExpert {
    public void getDescription();
}

class Welder implements DoorFittingExpert {
    public void getDescription() {
        System.out.println("I can only fit iron doors");
    }
}

class Carpenter implements DoorFittingExpert {
    public void getDescription() {
        System.out.println("I can only fit wooden doors");
    }
}
```

+++

## 实现(待续)

```java
interface DoorFactory {
    public Door makeDoor();
    public DoorFittingExpert makeFittingExpert();
}

// 木头工厂返回木门和木匠
class WoodenDoorFactory implements DoorFactory {
    public Door makeDoor() {
        return new WoodenDoor();
    }

    public DoorFittingExpert makeFittingExpert() {
        return new Carpenter();
    }
}

// 铁门工厂返回铁门和对应安装专家
class IronDoorFactory implements DoorFactory {
    public Door makeDoor() {
        return new IronDoor();
    }

    public DoorFittingExpert makeFittingExpert() {
        return new Welder();
    }
}
```

+++

## 实现(完)

```java
DoorFactory woodenFactory = new WoodenDoorFactory();

Door door = woodenFactory。makeDoor();
DoorFittingExpert expert = woodenFactory.makeFittingExpert();

door.getDescription();  // 输出: I am a wooden door
expert.getDescription(); // 输出: I can only fit wooden doors

// 铁门工厂也一样
DoorFactory ironFactory = new IronDoorFactory();

Door door = ironFactory.makeDoor();
DoorFittingExpert expert = ironFactory.makeFittingExpert();

door.getDescription();  // 输出: I am an iron door
expert.getDescription(); // 输出: I can only fit iron doors

```

+++

## 运用

有一组相关的对象需要相继创建

---

# 建造者模式

创建不同特点的对象而避免构造函数污染。当一个对象都多种特点的时候比较实用。或者在创造逻辑里有许多步骤的时候。

+++

## 👎[伸缩套管式构造函数](http://codethataint.com/blog/telescoping-constructor-pattern-java/)
```java
Pizza(int size) { ... }        
Pizza(int size, boolean cheese) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni) { ... }    
Pizza(int size, boolean cheese, boolean pepperoni, boolean bacon) { ... }
```
#### 👎解决方法之一
```java
Pizza pizza = new Pizza(12);
pizza.setCheese(true);
pizza.setPepperoni(true);
pizza.setBacon(true);
```
+++

## 👍解决方法(建造者)
```java
public class Pizza {
  private int size;
  private boolean cheese;
  private boolean pepperoni;
  private boolean bacon;
 
  public static class Builder {
    //required
    private final int size;
 
    //optional
    private boolean cheese = false;
    private boolean pepperoni = false;
    private boolean bacon = false;
 
    public Builder(int size) {
      this.size = size;
    }
 
    public Builder cheese(boolean value) {
      cheese = value;
      return this;
    }
    ...
    public Pizza build() {
      return new Pizza(this);
    }
  }
 
  private Pizza(Builder builder) {
    size = builder.size;
    cheese = builder.cheese;
    pepperoni = builder.pepperoni;
    bacon = builder.bacon;
  }
}
...
Pizza pizza = new Pizza.Builder(12)
                       .cheese(true)
                       .pepperoni(true)
                       .bacon(true)
                       .build();
```

+++

## 运用

- Android: [ShareCompat.IntentBuilder](https://developer.android.com/reference/android/support/v4/app/ShareCompat.IntentBuilder.html) [NotificationCompat.Builder](https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html)
- IDE支持：https://www.jetbrains.com/help/idea/2016.3/replace-constructor-with-builder.html

---

# 原型模式

通过"克隆"已有的对象来创建新对象

+++

## 实现(待续)
```java
    /**
     * Retrieves a map of extended data from the intent.
     *
     * @return the map of all extras previously added with putExtra(),
     * or null if none have been added.
     */
    public Bundle getExtras() {
        return (mExtras != null)
                ? new Bundle(mExtras)
                : null;
    }
```

+++

## 实现(待续)
```java
public final class Bundle extends BaseBundle implements Cloneable, Parcelable {
    /**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    public Bundle(Bundle b) {
        super(b);
        mFlags = b.mFlags;
    }

    /**
     * Clones the current Bundle. The internal map is cloned, but the keys and
     * values to which it refers are copied by reference.
     */
    @Override
    public Object clone() {
        return new Bundle(this);
    }
}
```

+++

# 实现(完)

```java
public class BaseBundle {
/**
     * Constructs a Bundle containing a copy of the mappings from the given
     * Bundle.
     *
     * @param b a Bundle to be copied.
     */
    BaseBundle(BaseBundle b) {
        if (b.mParcelledData != null) {
            if (b.isEmptyParcel()) {
                mParcelledData = NoImagePreloadHolder.EMPTY_PARCEL;
            } else {
                mParcelledData = Parcel.obtain();
                mParcelledData.appendFrom(b.mParcelledData, 0, b.mParcelledData.dataSize());
                mParcelledData.setDataPosition(0);
            }
        } else {
            mParcelledData = null;
        }

        if (b.mMap != null) {
            mMap = new ArrayMap<>(b.mMap);
        } else {
            mMap = null;
        }

        mClassLoader = b.mClassLoader;
    }
}
```

--- 

# 依赖注入

+++

## 使用了工厂方法
```java
public class ServiceApi {
    private Client client;
    public ServiceApi(){
        client = HTTPClientFactory.createHTTPClient();
    }
}
```
> 有什么问题？
<!-- .element: class="fragment" -->

* “硬编码”初始化违反开闭原则  <!-- .element: class="fragment" -->
* 负责对象创建（甚至声明周期管理）违反单一职责原则  <!-- .element: class="fragment" -->

+++

所有的逻辑都被代码（ServiceApi）**控制**，使用它只能被动的接受它的依赖（HTTPClientFactory.createHTTPClient()返回的具体Client）

+++

## 寻找“接缝”

"**接缝**是指程序中的一些特殊的点，在这些点上你无需做任何修改就可以达到改动程序行为的目的。"  --《修改代码的艺术》

+++

## 接缝在哪里？

```java
public class ServiceApi {
    private Client client;
    public ServiceApi(){
        client = HTTPClientFactory.createClient();
    }
}
```
> 需要增加接缝
<!-- .element: class="fragment" -->

+++

## 1. 改造构造方法
```java
public class ServiceApi {
    private Client client;
    public ServiceApi(Client client){
        this.client = client;
    }
}
```

+++

## 2. 增加Setter
```java
public class ServiceApi {
    private Client client;
    public ServiceApi(){
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
```

+++

## 3. 通过继承改变行为
```java
public class ServiceApi {
    protected Client client;
    public ServiceApi(){
        client = HTTPClientFactory.createClient();
    }
}

public class MyServiceApi extends ServiceApi {
    public MyServiceApi(){
        client = RPCClientFactory.createClient();
    }
}
```

+++

## 4. 通过形参改变行为
```java
public class ServiceApi {

    public JSONObject get(Client client) {
        return client.get(...)
    }
}
```

+++

## 控制反转

对象在被创建的时候，由一个调控系统内所有对象的外界实体，将其所依赖的对象的引用传递给它，即**依赖**被**注入**到对象中。<!-- .element: class="fragment" -->

> "不要给我们打电话，我们会打给你"。--好莱坞原则 <!-- .element: class="fragment" -->

> “库”和“框架” <!-- .element: class="fragment" -->

+++ 

## 依赖注入

我们需要一个调控系统，这个调控系统中我们存放一些对象的实体，或者对象的描述; 在类A的实例创建过程中即创建了A依赖的B对象，通过类型或名称来判断将不同的对象注入到不同的属性中。

+++

## Java中常见的依赖注入方式

* 构造方法注入  <!-- .element: class="fragment" -->
* Setter注入  <!-- .element: class="fragment" -->
* 方法形参注入  <!-- .element: class="fragment" -->
* 注解注入（JSR330参考实现）  <!-- .element: class="fragment" -->

> 可以自己实现注入

+++

## 常见的Java注入库

* PicoContainer  <!-- .element: class="fragment" -->
* Guice  <!-- .element: class="fragment" -->
* Spring DI  <!-- .element: class="fragment" -->
* Dagger 2  <!-- .element: class="fragment" -->

+++

##　简单对比

Generic:  
https://stackoverflow.com/questions/2026016/google-guice-vs-picocontainer-for-dependency-injection
Android:  
http://blog.nimbledroid.com/2016/03/07/performance-of-dependency-injection-libraries.html

> 以Dagger 2为例介绍依赖注入框架 <!-- .element: class="fragment" -->

+++

## Dagger 2


+++ 

## 如何测试

 






---


## 结构型模式

* 适配器模式 Adapter
* 桥接模式 Bridge
* 组合模式 Composite
* 装饰器模式 Decorator
* 门面模式 Facade
* 享元模式 Flyweight
* 代理模式 Proxy


---

# 适配器模式



