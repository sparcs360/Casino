# Other Domains

## Interfaces

| Name                             | Description                                                      |
|----------------------------------|------------------------------------------------------------------|
| **Casino**                       | A venue where **Customer**s go to play games!                    |
| **Bank**                         | The owner of all **Account**s                                    |
| **Customer**                     | A patron of the **Casino**                                       |
| **Account**                      | A record of a **Customer**'s changing chip count (as a result of placing **Bet**) |
| **CustomerRole**                 | A function performed by a **Customer** in a particular situation |

## Class Model

![yuml](http://yuml.me/097b5cc9)

### [yuml](http://yuml.me/diagram/scruffy/class/draw) script

```
// Entities
[Casino|signIn();findRooms();signOut()]
[Customer|getNickName();getChipCount()]
[Bank|getChipCount();processBet();processWinnings()]
[Account|getChipCount();addChips();deductChips()]
[CustomerRole|getCustomer()]

// Inheritence
[Customer]^-[CustomerRole]

// Associations
[Casino]++1-visitors\n0..*>[Customer]
[Casino]++1-bank\n1>[Bank]
[Bank]++1-accounts\n0..*>[Account]
[Account]<>1-1>[Customer]
[CustomerRole]<>-1>[Customer]
```
