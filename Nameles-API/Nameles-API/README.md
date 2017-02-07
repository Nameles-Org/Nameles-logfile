Install the necessary dependencies for python:

	pip install djangorestframework Markdown django-filter django-crispy-forms

Deploy Nameless API:

	1. Set the Postgresql database correctly in the file nameless/settings.py
	2. Run the aplication with the following command: 
		python manage.py runserver 0.0.0.0:8000 (you can set another port instead of 8000)