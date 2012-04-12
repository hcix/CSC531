import urllib2
import ClientForm

request = urllib2.Request(path)
response = urllib2.urlopen(request)

forms = ClientForm.ParseResponse(response, backwards_compat=False)
response.close()
form = forms[0]

form.set_value(usr, name="username")
form.set_value(pw, name="password")

request2 = form.click()
try:
    response2 = urllib2.urlopen(request2)
except urllib2.HTTPError, response2:
    pass

acceptStr = "You have been logged in successfully."

isAccepted = (response2.read()).find(acceptStr)
response2.close()