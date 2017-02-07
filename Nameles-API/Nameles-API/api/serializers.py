from rest_framework import serializers
from .models import ReferrerDomainTable

class ReferrerDomainTableSerializer(serializers.ModelSerializer):

	class Meta:
		model = ReferrerDomainTable
		fields = ('referrer', 'total_151112', 'score_151112', 'total_151119', 'score_151119', 'total_151126', 'score_151126', 'total_151202', 'score_151202', ) 
