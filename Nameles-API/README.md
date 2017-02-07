# Nameles-API

The API is available in: http://nameless.netcom.it.uc3m.es/
    
### Use cases:

- Search by referrer: 

	/api/?referrer=<REFERRER>

E.g. http://nameless.netcom.it.uc3m.es/api/?referrer=omiyageya-san.com

- Search by particular score of a particular date: 

	/api/?score_<DATE>=<SCORE>

E.g.  http://nameless.netcom.it.uc3m.es/api/?score_151112=23

- Search by score or total greater than X: 

	/api/?score_<DATE>__gt=<SCORE>
        /api/?total_<DATE>__gt=<SCORE>

E.g.  http://nameless.netcom.it.uc3m.es/api/?score_151112__gt=10 

- Search by score or total greater or equal than X: 

	/api/?score_<DATE>__gte=<SCORE> 
        /api/?total_<DATE>__gte=<SCORE>

E.g.  http://nameless.netcom.it.uc3m.es/api/?total_151112__gte=10   

- Search by score or total less than X: 

	/api/?score_<DATE>__lt=<SCORE> 
        /api/?total_<DATE>__lt=<SCORE>

- Search by score or total less or equal than X: 
	
	/api/?score_<DATE>__lte=<SCORE> 
        /api/?total_<DATE>__lte=<SCORE>    
        
- Order by one field:

	/api/?ordering=score_<DATE>

E.g. 	Ascending: http://nameless.netcom.it.uc3m.es/api/?ordering=score_151112
	Descending: http://nameless.netcom.it.uc3m.es/api/?ordering=-score_151112
                 

Note: all the filters can be merged, that means for example if you want to combine more than one restriction you just have to add the "&" symbol between filters.

E.g.  http://nameless.netcom.it.uc3m.es/api/?score_151112__lte=50&total_151112__gte=1000
