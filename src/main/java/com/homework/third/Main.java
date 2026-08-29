package com.homework.third;

// 1. Стратегия
interface PaymentStrategy {
    void pay(double amount);
}

class CardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Оплата картой: " + amount);
    }
}

class CashPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Оплата наличными: " + amount);
    }
}

class PaymentContext {
    private PaymentStrategy strategy;

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void pay(double amount) {
        strategy.pay(amount);
    }
}

// 2. Цепочка обязанностей
abstract class Handler {
    protected Handler next;

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(String request);
}

class AuthorizationHandler extends Handler {
    @Override
    public void handle(String request) {
        if (request.equals("auth")) {
            System.out.println("Авторизация выполнена");
        } else if (next != null) {
            next.handle(request);
        }
    }
}

class LoggingHandler extends Handler {
    @Override
    public void handle(String request) {
        if (request.equals("log")) {
            System.out.println("Запрос записан в журнал");
        } else if (next != null) {
            next.handle(request);
        }
    }
}

class ValidationHandler extends Handler {
    @Override
    public void handle(String request) {
        if (request.equals("validate")) {
            System.out.println("Запрос проверен");
        } else if (next != null) {
            next.handle(request);
        }
    }
}

// 3. Билдер
class User {
    private final String name;
    private final int age;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    public static class Builder {
        private String name;
        private int age;
        private String email;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', age=" + age +
                ", email='" + email + "'}";
    }
}

// 4. Прокси
interface Image {
    void display();
}

class RealImage implements Image {
    private final String fileName;

    public RealImage(String fileName) {
        this.fileName = fileName;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Загрузка изображения: " + fileName);
    }

    @Override
    public void display() {
        System.out.println("Отображение изображения: " + fileName);
    }
}

class ImageProxy implements Image {
    private final String fileName;
    private RealImage realImage;

    public ImageProxy(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(fileName);
        }

        realImage.display();
    }
}

// 5. Декоратор
interface Coffee {
    String getDescription();
    double getCost();
}

class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Кофе";
    }

    @Override
    public double getCost() {
        return 100;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", молоко";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 30;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", сахар";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 10;
    }
}

// 6. Адаптер
interface USB {
    void connectUSB();
}

class OldDevice {
    public void connectOldPort() {
        System.out.println("Старое устройство подключено");
    }
}

class USBAdapter implements USB {
    private final OldDevice device;

    public USBAdapter(OldDevice device) {
        this.device = device;
    }

    @Override
    public void connectUSB() {
        device.connectOldPort();
    }
}

public class Main {
    public static void main(String[] args) {

        // Стратегия
        PaymentContext payment = new PaymentContext();
        payment.setStrategy(new CardPayment());
        payment.pay(1000);

        payment.setStrategy(new CashPayment());
        payment.pay(500);

        // Цепочка обязанностей
        Handler chain = new AuthorizationHandler();
        chain.setNext(new LoggingHandler())
                .setNext(new ValidationHandler());

        chain.handle("auth");
        chain.handle("log");
        chain.handle("validate");

        // Билдер
        User user = new User.Builder()
                .setName("Иван")
                .setAge(20)
                .setEmail("ivan@mail.ru")
                .build();

        System.out.println(user);

        // Прокси
        Image image = new ImageProxy("photo.jpg");

        // Изображение загружается только при первом display()
        image.display();
        image.display();

        // Декоратор
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);

        System.out.println(coffee.getDescription());
        System.out.println("Стоимость: " + coffee.getCost());

        // Адаптер
        OldDevice oldDevice = new OldDevice();
        USB usb = new USBAdapter(oldDevice);

        usb.connectUSB();
    }
}