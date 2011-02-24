from django.http import HttpResponse

def hello(request):
    return HttpResponse("hello world")

def helloParam(request, offset):
    return HttpResponse("hello world %s" % offset)