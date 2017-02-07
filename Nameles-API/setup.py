#! /usr/bin/env python
#
import os
# temporarily redirect config directory to prevent matplotlib importing
# testing that for writeable directory which results in sandbox error in
# certain easy_install versions
os.environ["MPLCONFIGDIR"] = "."

DESCRIPTION = "Nameles API tool"
LONG_DESCRIPTION = """\

Nameles API provides an API for obtain the scored results and the total count of impresions of a given referrer in a given date.

"""


DISTNAME = 'Nameles-API'
MAINTAINER = 'Patricia Callejo'
MAINTAINER_EMAIL = 'patricia.callejo@imdea.org'
LICENSE = 'MIT'
DOWNLOAD_URL = 'https://github.com/Nameles-Org/Nameles/Nameles-API'

try:
    from setuptools import setup
    _has_setuptools = True
except ImportError:
    from distutils.core import setup

def check_dependencies():
    install_requires = []

    try:
        import djangorestframework
    except ImportError:
        install_requires.append('djangorestframework')

    try:
        import Markdown
    except ImportError:
        install_requires.append('Markdown')	

    try:
        import django-filter
    except ImportError:
        install_requires.append('django-filter')

    try:
        import django-crispy-forms
    except ImportError:
        install_requires.append('django-crispy-forms')

if __name__ == "__main__":

    install_requires = check_dependencies()

    setup(name=DISTNAME,
        author=MAINTAINER,
        author_email=MAINTAINER_EMAIL,
        maintainer=MAINTAINER,
        maintainer_email=MAINTAINER_EMAIL,
        description=DESCRIPTION,
        long_description=LONG_DESCRIPTION,
        license=LICENSE,
        download_url=DOWNLOAD_URL,
        install_requires=install_requires,
        packages=['Nameles-API'],
        classifiers=[
                     'Intended Audience :: Science/Research',
                     'Programming Language :: Python :: 2.7',
                     'Operating System :: POSIX',
                     'Operating System :: Unix',
                     'Operating System :: MacOS'],
          )
