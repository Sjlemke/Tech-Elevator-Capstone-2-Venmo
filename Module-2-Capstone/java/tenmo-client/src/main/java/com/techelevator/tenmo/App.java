package com.techelevator.tenmo;

import java.util.*;

import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoApplicationServices;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private TenmoApplicationServices tenmoApplicationServices;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
		Integer currUserId = currentUser.getUser().getId();
		System.out.printf("Your current balance is: %.2f TE bucks\n", tenmoApplicationServices.getBalance(currUserId));
	}

	private void viewTransferHistory() {
		List<Transfers> allTransfers = tenmoApplicationServices.getAllTransfersByUserId(currentUser.getUser().getId());
		List<Integer> transfersIds = new ArrayList<Integer>();
		System.out.println("Transfer History\n");
		System.out.printf("%5s   %-20s  %-10s\n", "ID", "From/To", "Amount");
		System.out.println("----------------------------------------");
		for (Transfers aTransfer : allTransfers) {
			transfersIds.add(aTransfer.getTransferId());
			String otherUserToPrint = "";
			if (aTransfer.getAccountFrom() == tenmoApplicationServices.getAccountIdByUserId(currentUser.getUser().getId())) {
				otherUserToPrint = "To: " + tenmoApplicationServices.getUsernameByAccountId(aTransfer.getAccountTo());
			} else {
				otherUserToPrint = "From: " + tenmoApplicationServices.getUsernameByAccountId(aTransfer.getAccountFrom());
			}
			System.out.printf("%5s   %-20s  $%.2f\n", aTransfer.getTransferId(), otherUserToPrint, aTransfer.getAmount());
		}
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		Integer wantedTransferId = -1;
		do {
			try {
				System.out.print("\nEnter a transfer ID to view more details (0 to cancel): ");
				wantedTransferId = Integer.parseInt(input.next());
			} catch (Exception e) {
				System.out.println("Error: not a valid transfer ID");
				wantedTransferId = -1;
				break;
			}
			if (!transfersIds.contains(wantedTransferId) && wantedTransferId != 0) {
				System.out.println("Transfer ID not found");
			}
		} while (!transfersIds.contains(wantedTransferId) && wantedTransferId != 0);
		
		if (wantedTransferId > 0) {
			Transfers wantedTransfer = tenmoApplicationServices.getSingleTransfer(currentUser.getUser().getId(), wantedTransferId);

			System.out.println("Transfer ID:     " + wantedTransfer.getTransferId());
			System.out.println("From account:    " + tenmoApplicationServices.getUsernameByAccountId(wantedTransfer.getAccountFrom()));
			System.out.println("To account:      " + tenmoApplicationServices.getUsernameByAccountId(wantedTransfer.getAccountTo()));
			System.out.println("Transfer Type:   " + tenmoApplicationServices.getDescByTypeId(wantedTransfer.getTransferTypeId()));
			System.out.println("Transfer Status: " + tenmoApplicationServices.getDescByStatusId(wantedTransfer.getTransferStatusId()));
			System.out.printf("Send amount:     $%.2f\n", wantedTransfer.getAmount());
		}
	}
	
	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
		List<User> allUsers = tenmoApplicationServices.getAllUsers();
		List<Integer> usersIds = new ArrayList<Integer>();
		System.out.println("Users list:\n");
		System.out.printf("%5s   %-15s\n", "Id", "Username");
		System.out.println("-------------------------");
		for (User aUser : allUsers) {
			if (aUser.getId() != currentUser.getUser().getId()) {
				System.out.printf("%5s   %-15s\n", aUser.getId(), aUser.getUsername());
				usersIds.add(aUser.getId());
			}
		}
		
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		
		Integer toUserId = -1;
		do {
			try {
				System.out.print("\nEnter the ID to send to (0 to cancel): ");
				toUserId = Integer.parseInt(input.next());
			} catch (Exception e) {
				System.out.println("Error: not a valid user ID");
				toUserId = -1;
				break;
			}
			if (!usersIds.contains(toUserId) && toUserId != 0) {
				System.out.println("User ID not found");
			}
		} while (!usersIds.contains(toUserId) & toUserId != 0);
		
		if (toUserId > 0) {
			System.out.print("Enter the amount you want to transfer over: ");
			Double amountToTransfer = Double.parseDouble(input.next());
			
			Double currentBalance = tenmoApplicationServices.getBalance(currentUser.getUser().getId());
			if (currentBalance > amountToTransfer) {
				Transfers createdTransfer = tenmoApplicationServices.createTransfers(currentUser.getUser().getId(), toUserId, amountToTransfer);
				tenmoApplicationServices.doTransfer(currentUser.getUser().getId(), toUserId, createdTransfer);
				System.out.println("Success!");
			} else {
				System.out.println("Failure: Transfer amount is more than the current balance");
			}
		}
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				tenmoApplicationServices = new TenmoApplicationServices(API_BASE_URL, currentUser.getToken());
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
