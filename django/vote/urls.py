from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
from poll import views

admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^vote/', include('vote.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    (r'^admin/', include(admin.site.urls)),
    (r'^poll/list/$', views.pollList),
    (r'^poll/saveInput/$', views.pollSaveInput),
    (r'^poll/save/$', views.pollSave),
    (r'^js/(?P<path>.*)$', 'django.views.static.serve', {'document_root': 'D:/code/django/vote/js/'}),
)
