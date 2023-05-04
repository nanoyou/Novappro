from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By 
from selenium.webdriver.support.ui import Select

wd = webdriver.Chrome()

wd.get("http://localhost:8080/Novappro_war_exploded/index.jsp")

wd.implicitly_wait(5)

wd.find_element(By.ID, 'user_id').send_keys("20210005")
wd.find_element(By.ID, 'password_input').send_keys("shfd8901!!")
wd.find_element(By.ID, 'login_btn').click()
