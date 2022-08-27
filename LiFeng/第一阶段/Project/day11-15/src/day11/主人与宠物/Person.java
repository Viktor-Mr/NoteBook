package day11.主人与宠物;


public class Person {
    String name;
    public Person(String name){
        this.name  = name;
    }
    public void feed(Pet pet){
        System.out.println("主人"+ name + "开始喂食了");
        pet.eat();
    }
    public void use(Pet pet){
        if (pet instanceof Dog){
            Dog dog = (Dog) pet;
            dog.guard();


            // ((Dog) pet).guard(); 或者
        }

        else if (pet instanceof  Cat){
            Cat cat  = (Cat)pet;
            ((Cat) pet).getMouse();
        }
    }
}
