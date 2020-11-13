## Manual Editing
When changing the configuration of a database in SQL, avoid manually changing the table from MySQL Workbench, all changes must be completed using a
database migration document, the file path to this folder is: `AgentDiscoveries-Backend/src/main/resources/db/migration`

## Making Migrations & Naming Conventions
Do not edit or delete any of the existing documents in db.migration. To make your changes you will need to create a new file in this folder.

When naming the file please keep to the set naming convention:

-	Files should be versioned, if the latest file is V8, your file should begin with V9
-	Once you state the version, you need two underscores e.g. `V9__`
-	If you are adding to the db, you then use the key word add (there are different keywords dependant on the action you wish to take)  e.g. `V9__add`
-	Between words do not use spaces, instead use underscores
-	After stating the key word, give a brief overview of what you will be editing e.g. adding a latitude and longitude feature - file will be called `V9__add_latitudelongitude`

## Keywords
Below is a list of key words to be used when naming the file:
-	add – this is for adding something to the database e.g. adding a new table or column in a table
-	remove – this is for removing something from the database e.g. removing a table or a column in a table
-	edit – this is to edit something existing in the database e.g. changing a datatype of a column or changing a table name