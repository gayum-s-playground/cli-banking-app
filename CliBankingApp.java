import java.util.Arrays;
import java.util.Scanner;
public class CliBankingApp{
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {

        
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String COLOR_GREEN_BOLD = "\033[33;1m";
        final String RESET = "\033[0m";

        final String DASHBOARD = " Welcome to Smart Banking App";
        final String CREATE_NEW_ACCOUNT = " Create New Account";
        final String DEPOSITE = " Deposite";
        final String WITHDRAWALS = " Withdrawals";
        final String TRANSFER = " Transfer";
        final String CHECK_ACCOUNT_BALANCE = " Check Account Balance";
        final String DELETE_ACCOUNT = " Delete Account";

        final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
        final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);

        String screen = DASHBOARD;

        String[][] userDetails = new String[0][2];
        double[] userAccountBal = new double[0];
        int generateIndex =1;
        int indexToSearch=0;

        
        do{
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t\t" + APP_TITLE + "\n");

            
            switch(screen){
                case DASHBOARD: 

                    System.out.println("\t[1]. Create New Account");
                    System.out.println("\t[2]. Deposite");
                    System.out.println("\t[3]. Withdrawals");
                    System.out.println("\t[4]. Transfer");
                    System.out.println("\t[5]. Check Account Balance");
                    System.out.println("\t[6]. Delete Account");
                    System.out.println("\t[7]. Exit\n");
                    System.out.print("\tEnter an option to continue: ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();
                    
                    switch (option){
                        case 1: screen = CREATE_NEW_ACCOUNT; break;
                        case 2: screen = DEPOSITE; break;
                        case 3: screen = WITHDRAWALS; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = CHECK_ACCOUNT_BALANCE; break;
                        case 6: screen = DELETE_ACCOUNT; break;
                        case 7: System.out.println(CLEAR); System.exit(0);
                        default: continue;
                    }
                    break ; 

                case CREATE_NEW_ACCOUNT:
                    
                    //Id generate
                    System.out.printf("\tNew Student ID: SDB-%05d \n", (generateIndex));

                    boolean valid;
                    String name;
                    //Name validation
                    do{
                        valid = true;
                        System.out.print("\tEnter User Name: ");
                        name = SCANNER.nextLine().strip();
                        if (name.isBlank()){
                            System.out.printf("\t%sName can't be empty%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                            continue;
                        }
                        for (int i = 0; i < name.length(); i++) {
                            if (!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i))) ) {
                                System.out.printf("\t%sInvalid Name%s\n", COLOR_RED_BOLD, RESET);
                                valid = false;
                                break;
                            }
                        }
                    }while(!valid);
                   
                    String[][] newDetailsArray = new String[generateIndex][2];

                    for (int row = 0; row < userDetails.length; row++) {
                        for (int column = 0; column < userDetails[row].length; column++) {
                            newDetailsArray[row][column]=userDetails[row][column];
                        }
                    }
                    newDetailsArray[newDetailsArray.length-1][0]=String.format("SDB-%05d", generateIndex);
                    newDetailsArray[newDetailsArray.length-1][1]=name;

                    userDetails=newDetailsArray;



                    for (int i = 0; i < userDetails.length; i++) {
                        System.out.println(Arrays.toString(userDetails[i]));
                    }                    

                    boolean balance = true;
                    double initialDipo;
                    do{
                        System.out.print("\tEnter Initial Deposite: ");
                        initialDipo = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        if(initialDipo<0){
                            System.out.printf(ERROR_MSG,"Invalied amount!");
                            balance = false;
                        }else if(initialDipo<5000){
                            System.out.printf(ERROR_MSG,"Insufficient amount!");
                            balance = false;
                        }else {
                            balance = true;
                        }

                    }while(!balance);

                    double[] newAccountBal = new double[userAccountBal.length+1];
                    for (int i = 0; i < userAccountBal.length; i++) {
                        newAccountBal[i]=userAccountBal[i];
                    }
                    newAccountBal[newAccountBal.length-1]=initialDipo;
                    userAccountBal=newAccountBal;

                    //System.out.println(Arrays.toString(userAccountBal));
                    
                    System.out.println();
                    System.out.printf(SUCCESS_MSG, String.format("SDB-%04d:%s has been saved successfully", generateIndex, name));
                    generateIndex++;
                    System.out.print("\tDo you want to continue adding (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;

                case DEPOSITE:
                    
                    //A/C number validation
                    indexToSearch = accountNumberValidation(ERROR_MSG, userDetails);
                    //System.out.println(indexToSearch);
                    System.out.printf("CurrentBalance : Rs %,.2f\n",userAccountBal[indexToSearch]);
                    double amount=0;
                    do{
                        System.out.print("Enter Deposite amount : ");
                        amount = SCANNER.nextDouble();
                        SCANNER.nextLine();
                        if(amount<500){
                            System.out.printf(ERROR_MSG,"Insufficent amount for deposite..Minimum Deposite amount is Rs 500.00/=");
                            continue;
                        }else{
                            break;
                        }
                    }while(true);

                    userAccountBal[indexToSearch]+=amount;
                    
                    System.out.printf("New balance : Rs %,.2f\n",userAccountBal[indexToSearch]);
                    
                    System.out.print("\tDo you want to continue  (Y/n)? ");
                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;

                case WITHDRAWALS:

                    

            }       
                
        }while(true);

    }

    public static int accountNumberValidation(String ERROR_MSG,String[][] userDetailStrings){
        int index = -1;
        boolean validation = true;
        String errorMsg = ERROR_MSG;
        String[][] details = userDetailStrings;

        
        do{
            System.out.print("Enter Account Number : ");
            String accountNumber = SCANNER.nextLine().strip();
            if(accountNumber.isBlank()){
                System.out.printf(ERROR_MSG, "Account number can't be empty!");
                validation=false;
                continue;
            }
            if(!accountNumber.startsWith("SDB-") || accountNumber.length()!= 9){
                System.out.printf(errorMsg,"Invalied Format!");
                validation=false;
                continue;
            }
            for (int i = 0; i < details.length; i++) {
                String id = details[i][0];
                if (accountNumber.equals(id)) {
                    index=i;
                    validation = true;
                    break;
                }
            }
            if(index==-1){
                System.out.printf(errorMsg,"Account number not found!");
            }

        }while(!validation);
        return index;
    }
}