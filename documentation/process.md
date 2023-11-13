## Process to refactor

* Comprendre le code
* rendre le code plus lisible en gardant dans la même classe
  * remove dead code
  * extract method
  * create variable
  * replace loop with pipeline
* separation code
* ajout nouvelle fonctionnalité

Apply:

- TDD rules
- Pattern Catalog


### TDD rules



### Pattern catalog

- https://refactoring.com/catalog/removeDeadCode.html
- https://refactoring.com/catalog/extractFunction.html
- https://refactoring.com/catalog/extractVariable.html

- https://refactoring.com/catalog/replaceLoopWithPipeline.html


## My process
* write I understand of the code in note.md file
* split loop
* extract loop in methods creating before a Data object to save calculated values.
* refactorize dataTemp
* Add method to display
  * extract ligne 69 into private method


### Extract loop in method


* with the simple extract method, the test fails because the sentence to be displayed is not the same; 
the value is not retained with a in/out argument.
I need to create object DataTemp (it will be rename later if needed) .




## refactorize dataTemp
At this setp I have a DataTemp object, but I can add in it the frequentRenderPoint ad the initialization.

## update DataTemp and extract the result construction from statement method
After the addToStatement  method has been moved into DataTempt, we can see that thetotalAmout and the result (displayed sentences)
are calculated fields. from each rentals.
Also I'll create a new field (Map String, doule) which will contain the amount of each title

