import React, { Component }  from 'react';
const Container  = props => (
    <div style={{width:'1500px',margin: '0 auto',padding:'auto'}}>
        {props.children}
    </div>
);

export default Container;