package otherPackedge;

import com.company.Tester;

public class ExamplesInOtherPackedge {
    public static void main(String[] args) {
        Tester tester1 = new Tester("Igor", "Ivanov", 3, "intermidiate",
                1400.20);
        //Only public Constructor could be used.
        tester1.printAllInformation();
        tester1.setName("Nikolay");
        tester1.setSurname("Petrov");
        tester1.setExperienceInYears(5);
        tester1.setEnglishLevel("upper intermidiate");
        tester1.setSalary(2500.50);
         //Friendly "set-methods" cannot be called from the other packedge.
        System.out.println(tester1.getName());
        System.out.println(tester1.getSurname());
        System.out.println(tester1.getExperienceInYears());
        System.out.println(tester1.getEnglishLevel());
        System.out.println(tester1.getSalary());
        // Friendly "get-methods" cannot be called from the other packedge.
        System.out.println(tester1.getExperienceInMonths());
        // This is also friendly Method
        tester1.printNameAndSurname();
        // Protected method cannot be called from the other packedge.
        tester1.printAllInformation();
        System.out.println(tester1.getSalaryMultTwo());
        //Privat Method cannot be called from the other Java Class.

    }
}
