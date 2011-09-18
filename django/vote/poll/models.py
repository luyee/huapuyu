from django.db import models

class Poll(models.Model):
    id = models.BigIntegerField(primary_key=True)
    title = models.CharField(max_length=50, null=False)
    enable = models.BooleanField(default=True)

    class Meta:
        db_table = 'tb_poll'


class PollItem(models.Model):
    id = models.BigIntegerField(primary_key=True)
    title = models.CharField(max_length=50, null=False)
    enable = models.BooleanField(default=True)
    poll = models.ForeignKey(Poll)

    class Meta:
        db_table = 'tb_poll_item'
