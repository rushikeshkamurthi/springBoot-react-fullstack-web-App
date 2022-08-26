
import React from "react";
import Container from "./Container";
import { Avatar, Button } from "antd";
import './Footer.css';

const Footer = (props) => {
   return ( <div className="footer">
   <Container>
       {props.numerOfStudents !==undefined ? <Avatar size='large'>{props.numerOfStudents}</Avatar>:null}
       <Button type="primary" onClick={props.openStudentModel}>Add New Student</Button>
   </Container>
</div>)

}

export default Footer;