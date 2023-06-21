public class Test {
    public static void main(String[]args){
        String name = "John Doe";
        String formattedString = String.format("Hello, {0}!", name);
        System.out.println(formattedString);
    }
}
