from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By 
from selenium.webdriver.support.ui import Select

wd = webdriver.Chrome()

wd.get("http://localhost:8080/Novappro_war_exploded/index.jsp")

wd.implicitly_wait(5)

wd.find_element(By.ID, 'user_id').send_keys("20210002")
wd.find_element(By.ID, 'password_input').send_keys("1234567890")
wd.find_element(By.ID, 'login_btn').click()

wd.implicitly_wait(5)
wd.find_element(By.XPATH, '/html/body/div/a').click()

wd.implicitly_wait(5)
wd.find_element(By.ID, '/html/body/form/table/tbody/tr[3]/td[9]/label/input').click()

wd.implicitly_wait(5)
wd.find_element(By.ID, '/html/body/form/label[1]/input').text = "这门课真的爱了爱了."

wd.implicitly_wait(5)
wd.find_element(By.ID, '/html/body/form/label[2]/input').click()