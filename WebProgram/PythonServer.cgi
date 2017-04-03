#!/usr/bin/python
from wsgiref.handlers import CGIHandler
from PythonServer import app

CGIHandler().run(app)