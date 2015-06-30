from django.db import models

#default setting
#null=False
#blank=False
class Area(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=50)
    type = models.SmallIntegerField(default=0)
    parent = models.ForeignKey('self', null=True)
    enable = models.BooleanField(default=True)

class Data(models.Model):
    id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=50)
    type = models.SmallIntegerField()
    enable = models.BooleanField(default=True)

class House(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)
    province = models.ForeignKey(Area, related_name='province')
    city = models.ForeignKey(Area, related_name='city')
    district = models.ForeignKey(Area, related_name='district')
    address = models.CharField(max_length=100)
    bedroom_count = models.SmallIntegerField()
    living_room_count = models.SmallIntegerField()
    kitchen_count = models.SmallIntegerField()
    washroom_count = models.SmallIntegerField()
    balcony_count = models.SmallIntegerField()
    prop_mgt_type = models.ForeignKey(Data, null=True, related_name='prop_mgt_type')
    direction = models.ForeignKey(Data, null=True, related_name='direction')
    decoration = models.ForeignKey(Data, null=True, related_name='decoration')
    total_floor = models.SmallIntegerField(null=True)
    floor = models.SmallIntegerField(null=True)
    construct_year = models.ForeignKey(Data, null=True, related_name='construct_year')
    transport = models.CharField(max_length=500, null=True)
    environment = models.CharField(max_length=500, null=True)
    remark = models.CharField(max_length=500, null=True)



