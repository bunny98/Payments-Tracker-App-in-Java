# Payments Tracking Application

A payments tracking application which allows users in a group, to add expenses they do on behalf of the members in the group. The application divides the expenses amongst 
all the members of the group equally. The final amounts to be paid by the users has been opitmized so that minimum number of transactions are required to settle all the expenses 
amongst the group members.

## Class Description

1. **ExpensesDB, GroupDB, UserDB** : Interface to underlying databases which store the expenses, groups and user information respectively. 
2. **Group, User, Expense** : Model classes to encapculate the group, user and expense data respectively. Also contains various operations required to be performed on each of the models for the application to divide the expenses in an optimal way.
3. **Balance, BalanceComparator** : Balance is a model class that stores the balance amount which a user has to pay to the other user. BalanceComparator is a comparator class used which sorts various Balance objects according to the balance amounts. 
4. **HeapGraphAlgorithm** : It is an implementation of the **IGraphAlgorithm** interface which implements a heap based algorithm to calculate the optimize the total number of transactions needed to settle the current expenses in a group.
5. **ExpenseFactory** : It is a factory class which creates Expenses, stores them in the ExpensesDB and recalculates the transactions required to settle this and all the previous expenses in a group.
6. **PaymentTrackerApplication** : The main class which creates users, groups and expenses. Displays the optimized transactions requried to settle all the expenses in a group, in the following pattern:
   <br> 
   <USER1> OWES <USER2> --> <AMOUNT>
