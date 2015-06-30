from datetime import *
from django.shortcuts import render_to_response
from poll.models import Poll, PollItem

def pollList(request):
    pollList = Poll.objects.order_by('-createTime')[:10]
    return render_to_response('pollList.html', {'pollList': pollList})


def pollSaveInput(request):
    return render_to_response('pollSave.html')


def test(request):
    return render_to_response('test.html')


def pollSave(request):
#    if request.method == 'POST':
#        if not request.POST.get('subject', ''):
#            errors.append('Enter a subject.')
#        if not request.POST.get('message', ''):
#            errors.append('Enter a message.')
#        if request.POST.get('email') and '@' not in request.POST['email']:
#            errors.append('Enter a valid e-mail address.')
#        if not errors:
#            send_mail(
#                request.POST['subject'],
#                request.POST['message'],
#                request.POST.get('email', 'noreply@example.com'),
#                ['siteowner@example.com'],
#            )
#            return HttpResponseRedirect('/contact/thanks/')
    title = request.POST.get('title', '')
    remark = request.POST.get('remark', '')
    poll = Poll(title=title, remark=remark, createTime=datetime.now())
    poll.save()

    itemIds = request.POST.get('item_ids', '')
    ids = itemIds.split(',')
    for id in ids:
        pollItem = PollItem(title=request.POST.get('item_title' + id, ''), poll=poll)
        pollItem.save()

    return render_to_response('pollSave.html')