package day11.主人与宠物;

public class test {
    public static void main(String[] args){
        Person p = new Person("张三");
        Pet  pet;
        pet = new Dog();
        //pet.eat();
        p.feed(pet);
        p.use(pet);

        System.out.println("-------------------");

        pet = new Cat();
        //pet.eat();
        p.feed(pet);
        p.use(pet);
    }
}
