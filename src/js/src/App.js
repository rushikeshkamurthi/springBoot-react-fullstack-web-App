import { Table, Avatar, Spin, Modal, Empty } from 'antd';
import { useEffect, useState } from 'react';
import './App.css';
import { getAllStudents } from './client';
import Container from './Container';
import Footer from './Footer';
import AddStudentForm from './forms/AddStudentForm';
import { errorNotification, sucessNotification } from './notification';
import React, { Component }  from 'react';

function App() {
  const [isAddStudentModelVisible, setisAddStudentModelVisible] = useState(false);
  const [students, setstudents] = useState([]);
  const [isFetching, setisFetching] = useState(false);
  const fetchStudents = () => {
    setisFetching(true);
    getAllStudents()
      .then(res => res.json()
        .then(students => {
          setstudents(students);
          setisFetching(false);
        })).catch(error =>{
          console.log("error response:" , error.error);
          errorNotification(error.error.httpStatus,error.error.message );
          setisFetching(false);
        });
  }



  useEffect(() => {
    fetchStudents();
    

  }, [])


  const commonElement = () => {
    return (
      <Container>
        <Modal title="Add Student" visible={isAddStudentModelVisible} onOk={closeStudentModel} onCancel={closeStudentModel}
            width={1000}>
            <AddStudentForm onsuccess={() => {
              closeStudentModel();
              fetchStudents();
              sucessNotification("Success","Successfuly added student to the database");
            }} 
            
            onFailure = {(err)=> {
              console.log('error:',err)
              errorNotification(err.statusText,err.error.message);
            }}
            />
          </Modal>
          <Footer
            numberOfStudent={students.length}
            openStudentModel={openStudentModel}/>
      </Container>
    );
  }
  const columns = [
    {
      title: '',
      key: 'studentId',
      render: (text, student) => (
        <Avatar size='large' >{`${student.firstName.charAt(0).toUpperCase()}${student.lastName.charAt(0).toUpperCase()}`}</Avatar>
      )
    },
    {
      title: 'ID',
      dataIndex: 'studentId',
      key: 'studentId',
    },
    {
      title: 'First Name',
      dataIndex: 'firstName',
      key: 'firstName',
    },
    {
      title: 'Last Name',
      dataIndex: 'lastName',
      key: 'lastName',
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
    },
  ];
  const openStudentModel = () => {
    setisAddStudentModelVisible(true);
  }
  const closeStudentModel = () => {
    setisAddStudentModelVisible(false);
  }
  return (
    <><Container>
      {isFetching && <Spin size='large'> </Spin>}
    </Container>
      { (!isFetching && students.length > 0 ) &&
       <Container>
         <Table 
         dataSource={students} 
         columns={columns} 
         rowKey="studentId" 
         /> 
         {commonElement()}
      </Container>}
      {students.length === 0 &&
     <Container>
       <Empty description={<h1>No Student found</h1>}/>
       {commonElement()}
     </Container>
       
       }
    </>
  );
}

export default App;
