# Libarian System

This repository contains codes that construct a libarian system allowing user, a libaraian, to issue patron cards, check in/out books, and display overdue notices

### Classes
- Book: A class that creates book object recording its title, author, checkout date, and due date
- Calendar: A class that counts the day passed once the system starts 
- Library: The main class of the libarian system to read input from libarian 
- OverdueNotice: A class that checks whether a patron possess books that are overdue and display overdue notices
- Patron: A class that records the patron's name and the books being checked out or returned by the patron

### Unit Tests
- BookTest, CalendarTest, LibraryTest, OverdueNoticeTest, PatronTest: Test codes that test the above classes
