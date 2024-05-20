import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Text {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> list = new ArrayList();
        int counter = 0;
        String data = "";



        File file = new File("C:\\Users\\77772\\Desktop\\test-file-for-tel-book.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {

            System.out.println("Введите 1 для добавления");
            System.out.println("Введите 2 для вывода");
            System.out.println("Введите 3 для удаления");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    Person person = new Person();
                    System.out.println("Введите имя:");
                    person.setName(scanner.next());
                    System.out.println("Введите фамилию:");
                    person.setSurname(scanner.next());
                    System.out.println("Введите возраст:");
                    person.setAge(scanner.next());
                    data = person.getName() + "," + person.getSurname() + "," + person.getAge() + '\n';
                    System.out.println(data);

                    try (FileOutputStream fileOut = new FileOutputStream(file, true)) {
                        // перевод строки в байты

                        byte[] buffer = data.getBytes();
                        fileOut.write(buffer);
                        System.out.println("The file has been written");

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                    break;


                case 2:
                    try (FileInputStream fin = new FileInputStream(file)) {
                        System.out.printf("File size: %d bytes \n", fin.available());
                        counter = 0;
                        data = "";
                        int i;

                        while ((i = fin.read()) != -1) {
                            char symbol = (char) i;
                            if (symbol == '\n' && counter > 0) {
                                String[] split = data.split(",");
                                Person person2 = new Person();
                                person2.setName(split[0]);
                                person2.setSurname(split[1]);
                                person2.setAge(split[2]);
                                list.add(person2);
                                data = "";
                            } else {
                                if (symbol != '\n') {

                                    data += symbol;
                                }

                            }
                            counter++;
                        }
                        for (Person item: list
                             ) {
                            System.out.println(item.getName()+" "+item.getSurname()+" "+item.getAge());
                        }
                        list.clear();
                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }
                    break;
                case 3: {
                    boolean flag = true;
                    System.out.println("Enter the keyword of contact you want to delete:");
                    String keyword = scanner.next();
                    data = "";
                    counter = 0;

                    list.clear();
                    try (FileInputStream fin = new FileInputStream(file)) {
                        System.out.printf("File size: %d bytes \n", fin.available());

                        int i;
                        while ((i = fin.read()) != -1) {
                            char symbol = (char) i;
                            if (symbol == '\n' && counter > 0) {
                                String[] split = data.split(",");
                                Person person2 = new Person();
                                person2.setName(split[0]);
                                person2.setSurname(split[1]);
                                person2.setAge(split[2]);
                                if (person2.getAge().equals(keyword) ||
                                        person2.getSurname().equals(keyword) ||
                                        person2.getName().equals(keyword)
                                ) {
                                    flag = false;
                                } else {
                                    list.add(person2);

                                }
                                data = "";
                            } else {
                                if (symbol != '\n') {
                                    data += symbol;
                                }

                            }
                            counter++;
                        }
                        if (flag) {
                            System.out.println("Такого значения в файле нет!");
                            break;
                        }

                    } catch (IOException ex) {

                        System.out.println(ex.getMessage());
                    }


                    File tmp = new File("C:\\Users\\77772\\Desktop\\tmp-file-for-tel-book.txt");
                    try {
                        tmp.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try (FileOutputStream fileOut1 = new FileOutputStream(tmp, true)) {
                        // перевод строки в байты

                        for (int i = 0; i < list.size(); i++) {
                            if (!list.get(i).getName().equals(keyword) &&
                                    !list.get(i).getSurname().equals(keyword) &&
                                    !list.get(i).getAge().equals(keyword)
                            ) {
                                String data1 = list.get(i).getName() + "," + list.get(i).getSurname() + "," + list.get(i).getAge() + '\n';
                                byte[] buffer = data1.getBytes();
                                fileOut1.write(buffer);
                            }
                        }
                        System.out.println("The file has been written");
                        list.clear();


                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    file.delete();
                    System.out.println(tmp.renameTo(new File("C:\\Users\\77772\\Desktop\\test-file-for-tel-book.txt")));


                    break;
                }
            }


        }


    }
}
