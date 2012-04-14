package cui;

import java.io.*;

import mastermind.*;
import mastermind.core.*;

/**
 * Implements a console interface to the Pizza Delivery System.
 * 
 */
public class ConsoleUi
{
	PizzaDeliverySystem system;

	/**
	 * Shows a user-selectable menu with the given title and menu items.
	 * 
	 * @param titleText
	 *            The text to display at the top of the menu.
	 * @param items
	 *            The items to display in the menu.
	 * @return The index of the selected item.
	 */
	private int showMenu(String titleText, Object[] items)
	{
		int result = -1;
		while (result < 0)
		{
			System.out.println();
			System.out.println(titleText);
			System.out.println();

			for (int i = 0; i < items.length; i++)
			{
				System.out.printf("  %1$d. %2$s\r\n", i + 1, items[i]);
			}
			System.out.println();
			System.out.print("Select an option: ");
			try
			{
				String input = (new BufferedReader(new InputStreamReader(System.in))).readLine();
				if (input == null || input.isEmpty())
					result = 0;
				else
					result = Integer.parseInt(input) - 1;
				if (result > items.length)
					result = -1;
			}
			catch (Exception ex)
			{
				result = -1;
			}

			if (result < 0 || result > items.length)
			{
				System.out.println("\r\nInvalid option. Please enter a valid number.");
				result = -1;
			}
		}
		return result;
	}

	/**
	 * Prints a title surrounded by a box.
	 * 
	 * @param title
	 */
	private void printBoxedTitle(String title)
	{
		StringBuffer buffer = new StringBuffer(title.length() * 3 + 12);
		buffer.append('┌');
		for (int i = 0; i < title.length(); i++)
			buffer.append('─');
		buffer.append("┐\r\n│");
		buffer.append(title);
		buffer.append("│\r\n└");
		for (int i = 0; i < title.length(); i++)
			buffer.append('─');
		buffer.append("┘\r\n");

		System.out.print(buffer.toString());
	}

	/**
	 * Displays a menu on the console, allowing the user to select an item by
	 * index.
	 * 
	 * @param titleText
	 *            The title of the menu.
	 * @param items
	 *            An array of items to display in the menu.
	 * @return An item selected from the array.
	 */
	private void showRootMenu() throws IOException
	{
		for (;;)
		{
			int result = showMenu("Pizza Delivery System\r\nSelect a menu item.", new Object[] { "Order Options", "View Customers",
					"Simulation Status", "Exit" });
			switch (result)
			{
			case 0:
				showOrderMenu();
				break;
			case 1:
				if (system.getCustomerStore().getAllCustomers().size() <= 0)
				{
					System.out.println("\nThere are no existing customers in the system.");
				}
				for (Customer cust : system.getCustomerStore().getAllCustomers())
					System.out.println("\r\n" + cust);
				break;
			case 2:
				System.out.println("\r\nSimulation Status\r\n");
				Simulation sim = system.getSimulation();
				System.out.printf("Available Cooks: %d/%d\r\nAvailable Drivers: %d/%d\r\nAvailable Oven Space: %d/%d\r\n",
						sim.getAvailableCooks(), sim.getTotalCooks(), sim.getAvailableDrivers(), sim.getTotalDrivers(), sim.getAvailableOvenSpace(),
						sim.getTotalOvenSpace());
				break;
			default:
				System.out.println("Shutting down...");
				return;
			}
		}
	}

	private void showOrderMenu() throws IOException
	{
		for (;;)
		{
			int result = showMenu("Order Options", new Object[] { "View Existing Orders", "Place New Order", "Root Menu" });
			switch (result)
			{
			case 0:
				showPendingOrders();
				break;
			case 1:
				createOrder();
				break;
			default:
				return;
			}
		}
	}

	private void showPendingOrders() throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		if (system.getPendingOrders().isEmpty())
		{
			System.out.print("There are no orders at this time. Would you like to create one? [Y/N] ");
			String yesNo = reader.readLine();
			if (yesNo == null || yesNo.isEmpty() || yesNo.toLowerCase().equals("y"))
			{
				createOrder();
			}
			else
			{
				System.out.println("No orders are currently pending.");
			}
		}
		else
		{
			int i = 1;
			for (Order order : system.getPendingOrders())
			{
				System.out.println("Order " + i);
				System.out.println("State: " + order.getState());
				System.out.println("Customer:");
				System.out.println(order.getCustomer());
				System.out.println();
				System.out.println("Items:");
				for (OrderItem item : order.getItems())
					System.out.println(item);
				System.out.println("--------------------------------");
				
				i++;
			}
		}
	}

	/**
	 * Prompts the user to lookup or create a customer and returns it.
	 * @return The customer in the CustomerStore, or null to cancel.
	 * @throws IOException
	 */
	private Customer lookupCustomer() throws IOException
	{
		String standardNumber = new String();
		Customer result;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Read in a 10-digit phone number
		System.out.print("Enter 10 digit customer phone number: ");
		String number = reader.readLine();
		for (int i = 0; i < number.length(); i++)
		{
			if (Character.isDigit(number.charAt(i)))
			{
				standardNumber += number.charAt(i);
			}
		}
		if (standardNumber.length() != 10)
		{
			System.out.println("You must enter 10 digits. Customer creation cancelled.");
			return null;
		}

		result = system.getCustomerStore().lookUpCustomer(standardNumber);
		if (result == null)
		{
			System.out.print("Customer does not exist in the directory, would you like to create a customer file? [Y/N] ");
			String yesNo = reader.readLine();
			if (yesNo == null || yesNo.isEmpty() || yesNo.toLowerCase().equals("y"))
			{
				System.out.print("Name: ");
				String name = reader.readLine();
				if (name == null || name.isEmpty())
				{
					System.out.println("Invalid name. Customer creation cancelled.");
					return null;
				}

				System.out.print("Address: ");
				String address = reader.readLine();
				if (address == null || address.isEmpty())
				{
					System.out.println("Invalid address. Customer creation cancelled.");
					return null;
				}

				result = new Customer(name, address, standardNumber);
				System.out.println("\r\nCustomer Created:");
				System.out.println(result);
				system.getCustomerStore().addCustomer(result);
			}
			else
			{
				System.out.println("Customer creation cancelled.");
				return null;
			}
		}
		else
		{
			System.out.println("\r\nCustomer found:");
			System.out.println(result);
		}
		return result;
	}

	private void createOrder() throws IOException
	{
		System.out.println("\r\nOrder Creation\r\n");

		Customer customer = lookupCustomer();
		if (customer == null)
		{
			System.out.println("Order creation cancelled.");
			return;
		}

		Order order = new Order(customer);
		for (;;)
		{
			switch (showMenu("Item Type", new Object[] { "Add a pizza", "Add a side item", "Complete order", "Cancel order" }))
			{
			case 0:
				{
					PizzaBase base = system.getMenu().getPizzaBases().get(showMenu("Sizes", system.getMenu().getPizzaBases().toArray()));
					PizzaItem item = new PizzaItem(base);
					order.getItems().add(item);

					for (boolean doneWithToppings = false; !doneWithToppings;)
					{
						switch (showMenu("Toppings", new Object[] { "Add a topping", "Finish pizza" }))
						{
						case 0:
							PizzaTopping topping = system.getMenu().getToppings().get(showMenu("Toppings", system.getMenu().getToppings().toArray()));
							switch (showMenu(topping.getName() + " Placement", new Object[] { "Full", "Side 1", "Side 2" }))
							{
							case 0:
								topping = topping.clone(ToppingPlacement.Full);
								break;
							case 1:
								topping = topping.clone(ToppingPlacement.Side1);
								break;
							case 2:
								topping = topping.clone(ToppingPlacement.Side2);
								break;
							}
							item.getToppings().add(topping);
							break;
						case 1:
							doneWithToppings = true;
							break;
						}
					}
					break;
				}
			case 1:
				{
					SimpleItem item = system.getMenu().getSimpleItems().get(showMenu("Simple Items", system.getMenu().getSimpleItems().toArray()));
					order.getItems().add(item);
					break;
				}
			case 2:
				float totalTime = order.getEstimatedTime();
				System.out.println("Review Order\r\nPrice: $" + order.getTotalPrice() + "\r\nTime: " + ((int) totalTime / 60) + ":"
						+ ((int) totalTime % 60));
				System.out.println("The order has been successfully queued.");
				system.queueOrder(order);
				return;
			case 3:
				return;
			}
		}
	}

	public void run(PizzaDeliverySystem system, String[] args)
	{
		this.system = system;
		try
		{
			showRootMenu();
		}
		catch (IOException ex)
		{
			// An IOException indicates a serious problem with the system
			// console device. Abort.
		}
	}
}
