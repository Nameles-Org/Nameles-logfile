import django_filters
from django.shortcuts import render
from rest_framework import viewsets, authentication, permissions
from rest_framework import filters

from .models import ReferrerDomainTable
from .serializers import ReferrerDomainTableSerializer

class ReferrerDomainTableFilter(django_filters.rest_framework.FilterSet):

    total_151112__gte = django_filters.NumberFilter(name="total_151112", lookup_expr='gte')
    score_151112__gte = django_filters.NumberFilter(name="score_151112", lookup_expr='gte')
    total_151119__gte = django_filters.NumberFilter(name="total_151119", lookup_expr='gte')
    score_151119__gte = django_filters.NumberFilter(name="score_151119", lookup_expr='gte')
    total_151126__gte = django_filters.NumberFilter(name="total_151126", lookup_expr='gte')
    score_151126__gte = django_filters.NumberFilter(name="score_151126", lookup_expr='gte')
    total_151202__gte = django_filters.NumberFilter(name="total_151202", lookup_expr='gte')
    score_151202__gte = django_filters.NumberFilter(name="score_151202", lookup_expr='gte')

    total_151112__lte = django_filters.NumberFilter(name="total_151112", lookup_expr='lte')
    score_151112__lte = django_filters.NumberFilter(name="score_151112", lookup_expr='lte')
    total_151119__lte = django_filters.NumberFilter(name="total_151119", lookup_expr='lte')
    score_151119__lte = django_filters.NumberFilter(name="score_151119", lookup_expr='lte')
    total_151126__lte = django_filters.NumberFilter(name="total_151126", lookup_expr='lte')
    score_151126__lte = django_filters.NumberFilter(name="score_151126", lookup_expr='lte')
    total_151202__lte = django_filters.NumberFilter(name="total_151202", lookup_expr='lte')
    score_151202__lte = django_filters.NumberFilter(name="score_151202", lookup_expr='lte')

    total_151112__gt = django_filters.NumberFilter(name="total_151112", lookup_expr='gt')
    score_151112__gt = django_filters.NumberFilter(name="score_151112", lookup_expr='gt')
    total_151119__gt = django_filters.NumberFilter(name="total_151119", lookup_expr='gt')
    score_151119__gt = django_filters.NumberFilter(name="score_151119", lookup_expr='gt')
    total_151126__gt = django_filters.NumberFilter(name="total_151126", lookup_expr='gt')
    score_151126__gt = django_filters.NumberFilter(name="score_151126", lookup_expr='gt')
    total_151202__gt = django_filters.NumberFilter(name="total_151202", lookup_expr='gt')
    score_151202__gt = django_filters.NumberFilter(name="score_151202", lookup_expr='gt')

    total_151112__lt = django_filters.NumberFilter(name="total_151112", lookup_expr='lt')
    score_151112__lt = django_filters.NumberFilter(name="score_151112", lookup_expr='lt')
    total_151119__lt = django_filters.NumberFilter(name="total_151119", lookup_expr='lt')
    score_151119__lt = django_filters.NumberFilter(name="score_151119", lookup_expr='lt')
    total_151126__lt = django_filters.NumberFilter(name="total_151126", lookup_expr='lt')
    score_151126__lt = django_filters.NumberFilter(name="score_151126", lookup_expr='lt')
    total_151202__lt = django_filters.NumberFilter(name="total_151202", lookup_expr='lt')
    score_151202__lt = django_filters.NumberFilter(name="score_151202", lookup_expr='lt')
	
    class Meta:
       	model = ReferrerDomainTable
       	fields = ['referrer', 'total_151112', 'score_151112', 'total_151119', 'score_151119', 'total_151126', 'score_151126', 'total_151202', 'score_151202', 
	'total_151112__gte', 'score_151112__gte', 'total_151119__gte', 'score_151119__gte', 'total_151126__gte', 'score_151126__gte', 'total_151202__gte', 'score_151202__gte', 
	'total_151112__lte', 'score_151112__lte', 'total_151119__lte', 'score_151119__lte', 'total_151126__lte', 'score_151126__lte', 'total_151202__lte', 'score_151202__lte', 
	'total_151112__gt', 'score_151112__gt', 'total_151119__gt', 'score_151119__gt', 'total_151126__gt', 'score_151126__gt', 'total_151202__gt', 'score_151202__gt', 
	'total_151112__lt', 'score_151112__lt', 'total_151119__lt', 'score_151119__lt', 'total_151126__lt', 'score_151126__lt', 'total_151202__lt', 'score_151202__lt']

class ReferrerDomainTableViewSet(viewsets.ReadOnlyModelViewSet):
#    """API endpoint for listing and creating sprints."""
    
    queryset = ReferrerDomainTable.objects.all()
    serializer_class = ReferrerDomainTableSerializer
    filter_backends = (filters.DjangoFilterBackend,filters.OrderingFilter,)
    filter_class = ReferrerDomainTableFilter
