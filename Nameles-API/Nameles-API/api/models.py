# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.conf import settings
from django.db import models
from django.utils.translation import ugettext_lazy as _


class ReferrerDomainTable(models.Model):
    referrer = models.TextField(blank=True, null=True)
    total_151112 = models.BigIntegerField(blank=True, null=True)
    score_151112 = models.SmallIntegerField(blank=True, null=True)
    total_151119 = models.BigIntegerField(blank=True, null=True)
    score_151119 = models.SmallIntegerField(blank=True, null=True)
    total_151126 = models.BigIntegerField(blank=True, null=True)
    score_151126 = models.SmallIntegerField(blank=True, null=True)
    total_151202 = models.BigIntegerField(blank=True, null=True)
    score_151202 = models.SmallIntegerField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'referrer_domain_table'

#    def __str__(self):
#        return  "%s" % self.referrer
