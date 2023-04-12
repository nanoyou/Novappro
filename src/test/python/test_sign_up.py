from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By 
from selenium.webdriver.support.ui import Select

wd = webdriver.Chrome()

wd.get("http://localhost:8080/Novappro_war_exploded/signup.jsp")

wd.implicitly_wait(5)

wd.find_element(By.ID, 'username_input').send_keys("测试人")
wd.find_element(By.ID, 'password_input').send_keys("123456qwe!")
wd.find_element(By.ID, 'confirmed_password_input').send_keys("123456qwe!")
wd.find_element(By.ID, 'signup_btn').click()
