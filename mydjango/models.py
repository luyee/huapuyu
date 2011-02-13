from django.db import models

class Area(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=50, null=True)
    type = models.SmallIntegerField(null=True)
    parentId = models.IntegerField
    enable = models.BooleanField(null=True)

    def __unicode__(self):
        return self.name